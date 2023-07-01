package view;
import controller.GestorObjeto;
import controller.GestorProductos;
import controller.GestorSQlite;
import controller.GestorUsuarios;
import models.*;

import java.util.*;

public class IConsola {

    private static Scanner scanner = new Scanner(System.in);


    public static void mostrarMenuPrincipal(ArrayList<Producto> productos) {
        mostrarTitulo("BIENVENIDO A LA TIENDA HARD-TECH !");
        int selec = validarEntero("SELECCIONE UNA OPCIÓN...\n1) Ver todos los productos\n2) Buscar producto por nombre\n3) Buscar productos por categoria\n4) Ver carrito", 4);

        switch (selec){
            case 1:
                mostrarProductos(productos);
                break;
            case 2:
                String busqueda = validarTexto("INGRESE EL NOMBRE DEL PRODUCTO:");
                ArrayList<Producto> productosPorNombre = GestorProductos.obtenerProductosPorBusqueda(productos, busqueda);
                if (productosPorNombre.isEmpty()){
                    System.out.println();
                    System.out.println("NO SE HAN ENCONTRADO COINCIDENCIAS...");
                    mostrarMenuPrincipal(productos);
                }
                else{
                    mostrarProductos(productosPorNombre);
                }
                break;
            case 3:
                System.out.println("LAS CATEGORIAS SON LAS SIGUIENTES:");
                System.out.println();
                listarArticulos(GestorProductos.getCategorias());
                int categoria = validarEntero("SELECCIONE UNA CATEGORIA:",GestorProductos.getCategorias().size());
                mostrarProductos(GestorProductos.obtenerProductosPorCategoria(productos, categoria-1));
                break;
            case 4:
              //  mostrarMenuCarrito(GestorUsuarios.clienteActual.getCarrito());
                break;
            default:
                mostrarInvalido();
                mostrarMenuPrincipal(productos);
        }
        selec = validarEntero("PARA VER LOS DETALLES DE UN PRODUCTO, SELECCIONE SU ÍNDICE...", productos.size());
        mostrarMenuDetalleProducto(productos.get(selec-1));
    }

    private static void listarArticulos(ArrayList<String> articulos){
        for (int i = 0; i < articulos.size(); i++) {
            System.out.println((i+1) + ") " + articulos.get(i));
        }
        System.out.println();
        // TODO : Mover de lugar
    }

    public static void mostrarMenuDetalleProducto(Producto producto) {

        int input, cantidad;

        System.out.println();
        System.out.println("DETALLES DEL PRODUCTO: ");
        System.out.println();

        ArrayList<String> claves = new ArrayList<>();
        claves.addAll(producto.getAtributos().keySet());

        System.out.println(" - " + "Marca: " + producto.getMarca_id());
        System.out.println(" - " + "Modelo: " + producto.getModelo());

        for (int i = 0; i < claves.size(); i++) {
            System.out.println(" - " + claves.get(i) + ": " + producto.getAtributos().get(claves.get(i)));
        }
        System.out.println();
        System.out.println(" - " + "Disponibilidad: " + producto.getStock());

        System.out.println();
        input = validarEntero("SELECCIONE UNA OPCIÓN: \n1) Para agregar al carrito \n2) Para comprar \n3) Para volver\n", 3);
        cantidad = validarEntero("INGRESE LA CANTIDAD: ", 0);

        if (input==1) {
            if(cantidad <= producto.getStock()){
                if(GestorUsuarios.clienteActual.getCarrito().getProductos().containsKey(producto)) {
                    System.out.println("Se ha actualizado la cantidad del producto en el carrito!");
                    mostrarMenuDetalleProducto(producto);
                }
                else{
                    GestorUsuarios.clienteActual.getCarrito().getProductos().put(producto, cantidad);
                    System.out.println("Se ha agregado el producto al carrito!");
                    mostrarMenuDetalleProducto(producto);
                }
            }
            else{
                System.out.println("NO HAY SUFICIENTE STOCK DISPONIBLE!, VERIFIQUE LA DISPONIBILIDAD");
                mostrarMenuDetalleProducto(producto);
            }
        }
        if (input==2) {
            if((cantidad <= producto.getStock()) && (GestorUsuarios.clienteActual.getSaldo() >= (producto.getPrecio()*cantidad))) {
                producto.setStock(producto.getStock() - cantidad);
                GestorUsuarios.clienteActual.setSaldo(GestorUsuarios.clienteActual.getSaldo() - (producto.getPrecio() * cantidad));
                Pedido pedido = GestorUsuarios.clienteActual.crearPedido();
                pedido.setCliente(GestorUsuarios.clienteActual);
                HashMap<Producto, Integer> carrito = new HashMap<>();
                carrito.put(producto, cantidad);
                pedido.setCarrito(new Carrito(GestorUsuarios.clienteActual, carrito));
                pedido.setMetodoPago("Transfencia");
                mostrarMenuPago(pedido);

            } else if (cantidad > producto.getStock()) {
                System.out.println("NO HAY SUFICIENTE STOCK DISPONIBLE!, VERIFIQUE LA DISPONIBILIDAD");
                mostrarMenuDetalleProducto(producto);

            } else if (GestorUsuarios.clienteActual.getSaldo() < (producto.getPrecio()*cantidad)) {
                System.out.println("NO HAY SUFICIENTE SALDO DISPONIBLE!, VERIFIQUE SU SALDO");
                mostrarMenuDetalleProducto(producto);
            }
        }
        if (input==3) {
            mostrarMenuPrincipal(GestorProductos.getProductos());
        }

    }

    public static void mostrarMenuDetallePedido(Pedido pedido) {

        System.out.println("------------ DETALLE FACTURA DE COMPRA -----------");
        System.out.println("Fecha de creación: " + pedido.getFechaCreacion());
        System.out.println("Metodo de pago: " + pedido.getMetodoPago());

        if (pedido.getDespacho() != null){
            System.out.println("Dirrecion de despacho: " + pedido.getDespacho().getDireccion() + ", " + pedido.getDespacho().getComuna() + ", " + pedido.getDespacho().getRegion());
            System.out.println("Costo de despacho: " + pedido.getDespacho().getCostoDespacho());
        }
        else {
            System.out.println("*Sin despacho");
        }
        System.out.println("Total: " + pedido.getTotal());
    }
    public static void mostrarMenuUsuario() {

        System.out.println("DATOS DEL USUARIO:");
        mostrarDatos(GestorUsuarios.clienteActual);

        System.out.println("SELECCIONE ALGUNA OPCIÓN:");
        System.out.println("1) Modificar datos, 2) Cambiar contraseña, 3) Salir");

        int selec = scanner.nextInt();
        String nombre, email, direccion, password;

        if (selec==1){

            nombre = validarTexto("INGRESE SU NOMBRE");
            email = validarTexto("INGRESE SU EMAIL");
            direccion = validarTexto("INGRESE SU DIRECCIÓN");
            password = validarTexto("*INGRESE SU CONTRASEÑA PARA CONFIRMAR:");

            if(GestorUsuarios.clienteActual.verificarLogin(email, password))
            {
                GestorUsuarios.clienteActual.setNombre(nombre);
                GestorUsuarios.clienteActual.setNombre(email);
                GestorUsuarios.clienteActual.setNombre(direccion);
                System.out.println("DATOS DEL USUARIO MODIFICADOS CON EXITO!");
            }
            else{
                System.out.println("ERROR, LA CONTRASEÑA ES INCORRECTA!");
                mostrarMenuUsuario();
            }
        }

        if (selec==2){

            password = validarTexto("INGRESE SU CONTRASEÑA ANTERIOR:");
            if(GestorUsuarios.clienteActual.verificarLogin(GestorUsuarios.clienteActual.getEmail(), password))
            {
                String password1, password2;
                password1 = validarTexto("INGRESE SU NUEVA CONTRASEÑA:");
                password2 = validarTexto("VUELVA A INGRESARLA PARA CONFIRMAR:");

                if(password1==password2){
                    GestorUsuarios.clienteActual.setPassword(password1);
                    System.out.println("SU CONTRASEÑA HA SIDO CAMBIADA CON EXITO!");
                }
                else{
                    System.out.println("LAS CONTRASEÑAS INGRESADAS NO COINCIDEN!...");
                    mostrarMenuUsuario();
                }

            }
            else{
                System.out.println("ERROR, LA CONTRASEÑA ES INCORRECTA");
            }
        }
        if (selec==3){
            mostrarMenuPrincipal(GestorProductos.getProductos());
        }
    }

    private  static void listarCarrito(Carrito carrito){
        String borde  = "+---------------------------+------------+-------------------+%n";
        String borde2 = "+------------------------------------------------------------+%n";
        String formato = "| %-25s | %-10s | %-17s |%n";
        String formato2 = "| %-58s |%n";
        System.out.println();
        System.out.println("CARRITO:");
        System.out.format(borde, "");
        System.out.format(formato, "PRODUCTO : ", "CANTIDAD :", "PRECIO UNITARIO :");

        System.out.format(borde, "");
        System.out.format(formato2, "SUB TOTAL : $" + calcularTotalCarrito(carrito));
        System.out.format(borde2, "");
        System.out.println();

        // TODO : Terminar carrito
    }

    private static int calcularTotalCarrito(Carrito carrito){
        int total=0;
        for (Producto c : carrito.getProductos().keySet()){
            total += c.getPrecio();
        }
        return total;
    }

    /*public static void mostrarMenuCarrito(Carrito carrito) {
        int selec=0;
        listarCarrito(carrito.getProductos().keySet());
        if(carrito.getProductos().size() > 0){
            selec = validarEntero("SELECCIONE ALGUNA OPCIÓN: \n1) Proceder al pago, 2) Modificar artículo, 3) Vaciar carrito, 4) Salir", 4);
        }
        else{
            selec=0;
            System.out.println("EL CARRITO ESTÁ VACIO, OPRIMA CUALQUIER TECLA PARA SALIR...");
            scanner.nextLine();
            mostrarMenuPrincipal(GestorProductos.getProductos());
        }
        if(selec==1){
            if(GestorUsuarios.clienteActual.getSaldo() >= calcularTotalCarrito(carrito)) {

                for (int i = 0; i < carrito; i++) {
                    carrito.get(i).getProducto().setStock(carrito.get(i).getProducto().getStock() - 1);
                }
                GestorUsuarios.clienteActual.setSaldo(GestorUsuarios.clienteActual.getSaldo() - calcularTotalCarrito(carrito));
                Pedido p = new Pedido();
                p = GestorUsuarios.clienteActual.crearPedido();
                p.setCliente(GestorUsuarios.clienteActual);
                p.setCarrito(GestorUsuarios.clienteActual.getCarrito());
                p.setMetodoPago("Transfencia");
                mostrarMenuPago(p);

            } else {
                System.out.println("NO HAY SUFICIENTE SALDO DISPONIBLE!, VERIFIQUE SU SALDO");
                mostrarMenuCarrito(carrito);
            }
        }
        if(selec==2){

            int productoSelec, cantidad;
            for (int i = 0; i < carrito.size(); i++) {
                System.out.println((i+1) + ") " + carrito.get(i).getProducto().getMarca_id() + " " + carrito.get(i).getProducto().getModelo());
            }
            System.out.println();
            productoSelec = validarEntero("SELECCIONE UN PRODUCTO DE LA LISTA DEL CARRITO:", 1);

            System.out.println();
            cantidad = validarEntero("INGRESE LA CANTIDAD DEL NUEVO PRODUCTO, O PARA ELIMINAR DEL CARRITO:", 1);

            if(cantidad==0) {
                carrito.remove(productoSelec-1);
                System.out.println("PRODUCTO ELIMINADO DEL CARRITO...");
                System.out.println();
                mostrarMenuCarrito(carrito);
            }
            if(cantidad >= carrito.get(productoSelec-1).getProducto().getStock()) {
                carrito.get(productoSelec-1).setCantidad(cantidad);
            }
            else{
                System.out.println("EL PRODUCTO NO DISPONE DE STOCK SUFICIENTE, INGRESE OTRA CANTIDAD...");
                mostrarMenuCarrito(carrito);
            }
        }
        if(selec==3){
            carrito.clear();
            System.out.println("SE HA VACIADO TODA LA LISTA DEL CARRITO");
            mostrarMenuCarrito(carrito);
        }
        if(selec==4){
            mostrarMenuPrincipal(GestorProductos.getProductos());
        }
    }*/

    private static void mostrarDatos(Object objeto) {
        HashMap<String, String> atributosValores = GestorObjeto.obtenerAtributosObjeto(objeto);
        String borde = "+----------------------+----------------------------------+%n";
        for (Map.Entry<String, String> atributo : atributosValores.entrySet()) {
            String nombre = atributo.getKey();
            Object valor = atributo.getValue();
            System.out.format(borde);
            System.out.format("| %-20s | %-32s |%n", nombre, valor);
        }
        System.out.format(borde);
    }
    private static void mostrarTotal(int total) {
        System.out.format("+------------------------------------------------------------+%n");
        System.out.format("| %-58s |%n", "TOTAL : $" + total);
        System.out.format("+------------------------------------------------------------+%n");
    }
    public static void mostrarMenuPago(Pedido pedido) {

        mostrarTitulo("PROCEDIMIENTO PEDIDO");
        System.out.println("  DATOS DEL CLIENTE:");
        mostrarDatos(pedido.getCliente());
        System.out.println("RESUMEN DEL PEDIDO:");
        ArrayList<Producto> productosCarrito = (ArrayList<Producto>) pedido.getCarrito().getProductos().keySet();
        mostrarProductos(productosCarrito);
        int total = pedido.getCarrito().calcularPrecioTotal();
        mostrarTotal(total);
        System.out.println("  DATOS DEL DESPACHO:");
        mostrarDatos(pedido.getDespacho());
        total += pedido.getDespacho().getCostoDespacho();

        int input = validarEntero("1) PARA CONFIRMAR COMPRA, 2) PARA CANCELAR", 2);
        switch (input){
            case 1:
                System.out.println();
                System.out.println("¡GRACIAS POR SU COMPRA!");
                pedido.generarFactura();
                continuar();
                break;
            case 2:
                mostrarMenuPrincipal(GestorProductos.getProductos());
                break;
            default:
                mostrarInvalido();
                mostrarMenuPago(pedido);
                break;
        }
    }

    public static void mostrarProductos(ArrayList<Producto> productos){


        String borde = "+-----------------+----------------------+--------------+%n";
        String formato = "| %-15s | %-20s | %-12s |%n";

        System.out.println();
        System.out.println("LISTADO DE PRODUCTOS:");
        System.out.format(borde, "");
        System.out.format(formato, "MARCA : ", "MODELO : ", "PRECIO : ");

        for (int i = 0; i < productos.size(); i++) {
            System.out.format(borde);
            System.out.format(formato, (i+1) + ") " + GestorProductos.getMarcas().get(productos.get(i).getMarca_id()-1), productos.get(i).getModelo(), productos.get(i).getPrecio());
        }
        System.out.format(borde, "");
    }

    public static void mostrarTitulo(String titulo){
        System.out.println();
        System.out.println("---------- " + titulo + " ----------");
        System.out.println();
    }

    public static void mostrarInvalido(){
        System.out.println("ENTRADA INVALIDA, POR FAVOR REINTENTE...");
    }

    public static void continuar() {
        int input;
        System.out.println();
        input = validarEntero("DESEA SEGUIR EN LA TIENDA?\n1) SI, 2) NO", 2);

        switch (input) {
            case 1:
                mostrarMenuPrincipal(GestorProductos.getProductos());
                break;
            case 2:
                break;
            default:
                mostrarInvalido();
                continuar();
        }
    }

    public static void mostrarMenuPrincipalAdmin(){
        int selec;
        mostrarTitulo("MENU PRINCIPAL");
        selec = validarEntero("SELECCIONE UN ACCIÓN:\n1) Gestionar productos, 2) Gestionar pedidos, 3) Gestionar clientes, ", 3);

        if(selec==1) {
            System.out.println();
            mostrarProductos(GestorProductos.getProductos());
            selec = validarEntero("SELECCIONE UNA OPCIÓN:\n1) Agregar producto, 2) Modificar producto, 3) Eliminar producto, 4) Salir", 4);
            if(selec==1){
                mostrarMenuAgregarProducto();
            }
            if(selec==2){
                mostrarMenuModificarProducto();
            }
            if(selec==3){
                mostrarMenuEliminarProducto();
            }
            if(selec==4){
                mostrarMenuPrincipalAdmin();
            }
        }
    }

    public static void mostrarMenuAgregarProducto() {

        System.out.println();
        System.out.println("MARCAS:");
        for (int i = 0; i < GestorProductos.getMarcas().size(); i++) {
            System.out.println((i+1) + ") " + GestorProductos.getMarcas().get(i));
        }
        System.out.println();
        int marca_id = validarEntero("SELECCIONE LA MARCA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", GestorProductos.getMarcas().size());
        String marca = GestorProductos.getMarcas().get(marca_id-1);
        String modelo = validarTexto("INGRESE EL MODELO DEL PRODUCTO:");

        System.out.println();
        System.out.println("CATEGORÍAS:");
        for (int i = 0; i < GestorProductos.getCategorias().size(); i++) {
            System.out.println((i+1) + ") " + GestorProductos.getCategorias().get(i));
        }
        System.out.println();
        int categoria = validarEntero("SELECCIONE LA CATEGORIA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", GestorProductos.getCategorias().size());
        int stock = validarEntero("INGRESE EL STOCK DEL PRODUCTO:", 0);
        int precio = validarEntero("INGRESE EL PRECIO DEL PRODUCTO:", 0);

        Producto p = new Producto();

        p.setMarca_id(marca_id);
        p.setModelo(modelo);
        p.setCategoria_id(categoria);
        p.setStock(stock);
        p.setPrecio(precio);

        int input;
        for (int i = 0; i < 10; i++) {
            System.out.println();
            System.out.println("ATRIBUTOS EXTRAS:");
            input = validarEntero("1) Para agregar un atributo, 2) Para terminar", 2);

            if(input==1){
                for (int j = 0; j < GestorProductos.getAtributosExtras().size(); j++) {
                    System.out.println(j+1 + ") " + GestorProductos.getAtributosExtras().get(j));
                }
                System.out.println();
                int clave = validarEntero("SELECCIONE UN ATRIBUTO DE LA LISTA ANTERIOR", GestorProductos.getAtributosExtras().size());
                String valor;
                valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");
                p.addSpec(clave, valor);
                System.out.println("ATRIBUTO AGREGADO!");
            }
        }
        GestorProductos.addProducto(p);
        GestorSQlite.insertarRegistro("Producto", GestorObjeto.obtenerAtributosObjeto(p));

        HashMap<String, String> atributos = p.getAtributos();
        for (HashMap.Entry<String, String> atributo : atributos.entrySet()){
            HashMap<String, String> registro = new HashMap<>();
            HashMap atributo_id = GestorSQlite.obtenerRegistro("Atributo", "nombre", String.valueOf(atributo.getKey()));
            registro.put("producto_id", String.valueOf(p.getId()));
        }
        GestorSQlite.insertarRegistro("Atributo_Producto", p.getAtributos());

        System.out.println();
        System.out.println("HA AGREGADO EL PRODUCTO CON EXITO!");
        System.out.println();
        mostrarMenuPrincipalAdmin();
    }

    public static void mostrarMenuModificarProducto(){
        int indice = validarEntero("INGRESE EL ÍNDICE DEL PRODUCTO QUE DECEA MODIFICAR", GestorProductos.getProductos().size());

        System.out.println();
        System.out.println("MARCAS:");
        listarArticulos(GestorProductos.getMarcas());
        int marca_id = validarEntero("SELECCIONE LA MARCA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", GestorProductos.getMarcas().size());
        String marca = GestorProductos.getMarcas().get(marca_id-1);
        String modelo = validarTexto("INGRESE EL MODELO DEL PRODUCTO:");

        System.out.println();
        System.out.println("CATEGORÍAS:");
        listarArticulos(GestorProductos.getCategorias());
        System.out.println();
        int categoria = validarEntero("SELECCIONE LA CATEGORIA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", GestorProductos.getMarcas().size());
        int stock = validarEntero("INGRESE EL STOCK DEL PRODUCTO:", 0);
        int precio = validarEntero("INGRESE EL PRECIO DEL PRODUCTO:", 0);

        Producto p = GestorProductos.getProductos().get(indice-1);
        p.setMarca_id(marca_id);
        p.setModelo(modelo);
        p.setCategoria_id(categoria);
        p.setStock(stock);
        p.setPrecio(precio);

        for (int i = 0; i < 10; i++) {
            System.out.println();
            System.out.println("ATRIBUTOS EXTRAS:");
            indice = validarEntero("1) Para agregar un atributo, 2) Para editar un atributo, 3) Eliminar un atributo, 4) Para terminar", 2);

            if(indice==1){
                listarArticulos(GestorProductos.getAtributosExtras());
                int clave = validarEntero("SELECCIONE UN ATRIBUTO DE LA LISTA ANTERIOR", GestorProductos.getAtributosExtras().size());
                String valor;
                valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");
                p.addSpec(clave, valor);
                System.out.println("ATRIBUTO AGREGADO!");
            }

            if(indice==2){
                System.out.println("ATRIBUTOS EXTRAS DEL PRODUCTO:");
                listarArticulos(GestorProductos.getAtributosExtras());
                int keyAtributo  = validarEntero("INGRESE EL INDICE DEL ATRIBUTO QUE DECEA MODIFICAR:", 0);
                String clave = validarTexto("INGRESE EL NOMBRE DEL ATRIBUTO:");
                String valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");
                HashMap edit = new HashMap<>();
                edit.put(clave, valor);
                p.getAtributos().get(p.getAtributos().get(keyAtributo)).equals(edit);
            }

            if(indice==3){
                System.out.println("ATRIBUTOS EXTRAS DEL PRODUCTO:");
                listarArticulos(GestorProductos.getAtributosExtras());
                int keyAtributo  = validarEntero("INGRESE EL INDICE DEL ATRIBUTO QUE DECEA ELIMINAR:", 0);
                p.getAtributos().remove(keyAtributo);
                System.out.println();
                System.out.println("HA ELIMINADO EL ATRIBUTO CON EXITO!");
            }
            if(indice==4){ break; }
        }

        System.out.println();
        System.out.println("HA MODIFICADO EL PRODUCTO CON EXITO!");
        System.out.println();

        GestorProductos.getProductos().get(indice-1).equals(p);
        mostrarMenuPrincipalAdmin();
    }

    public static void mostrarMenuEliminarProducto(){

    }

    public static void mostrarMenuModificarPedido(Pedido pedido){

    }

    public static void mostrarMenuModificarCliente(){

    }

    public static int validarEntero(String mensaje, int opcionMaxima) {
        Scanner sc = new Scanner(System.in);
        int entrada = 0;
        boolean entradaValida = false;
        ArrayList<Integer> opcionesValidas = new ArrayList<>();
        for (int i = 1; i <= opcionMaxima; i++) {
            opcionesValidas.add(i);
        }
        do {
            System.out.print(mensaje);
            System.out.println();
            try {
                entrada = sc.nextInt();
                if (opcionesValidas.contains(entrada) || opcionMaxima == 0) {
                    entradaValida = true;
                } else {
                    System.out.println("OPCIÓN INVALIDA!");
                }
            } catch (Exception e) {
                System.out.println("DEBES INGRESAR UN NUMERO ENTERO!");
                sc.next();
            }
        } while (!entradaValida);
        return entrada;
    }

    public static String validarTexto(String mensaje) {
        Scanner sc = new Scanner(System.in);
        String entrada = "";
        boolean entradaValida = false;
        do {
            System.out.print(mensaje);
            System.out.println();
            try {
                entrada = sc.nextLine();
                if (!entrada.isEmpty()) {
                    entradaValida = true;
                } else {
                    System.out.println("DEBES INGRESAR UN TEXTO NO VACIO!");
                }
            } catch (Exception e) {
                System.out.println("HA OCURRIDO UN ERROR");
                sc.next();
            }
        } while (!entradaValida);
        return entrada;
    }

    public static void mostrarMenuLogin() {
        System.out.println();
        int opcion = validarEntero("SELECCIONE UNA OPCIÓN\n1) Para iniciar sesión, 2) Para crear cuenta, 3) Para ingresar como invitado", 3);

        if(opcion==1){
            String email = validarTexto("INGRESE SU EMAIL:");
            String password = validarTexto("INGRESE SU CONTRASEÑA:");
            Usuario usuario = GestorUsuarios.iniciarSesion(email, password);
            GestorUsuarios.addUsuario(usuario);

           if (usuario instanceof Cliente){
               mostrarMenuPrincipal(GestorProductos.productos);
           }
           else if(usuario instanceof Admin){
               mostrarMenuPrincipalAdmin();
           }
            System.out.println("NO SE HA ENCONTRADO UN USUARIO REGISTRADO, REINTENTE...");
            mostrarMenuLogin();
        }

        if(opcion==2){
            int tipoUsuario = validarEntero("SELECCIONE UNA OPCIÓN\n1) Cliente, 2) Administrador", 2);
            if(tipoUsuario==1) {
                crearUsuario(new Cliente());
                mostrarMenuPrincipal(GestorProductos.getProductos());
            }
            if(tipoUsuario==2) {
                crearUsuario(new Admin());
                mostrarMenuPrincipalAdmin();
            }
        }
        if(opcion==3){
            int saldo = validarEntero("INGRESE EL SALDO QUE DISPONE:",0);
            Cliente cliente = new Cliente();
            cliente.setSaldo(saldo);
            GestorUsuarios.addUsuario(cliente);
            mostrarMenuPrincipal(GestorProductos.getProductos());
        }
    }

    private static Usuario crearUsuario(Usuario usuario) {
        String rut = menuValidarRut();
        String nombre = validarTexto("INGRESE SU NOMBRE");
        String email = validarTexto("INGRESE SU EMAIL");
        String password = validarTexto("INGRESE SU CONTRASEÑA:");

        if(usuario instanceof Cliente){
            String direccion = validarTexto("INGRESE SU DIRECCIÓN");
            int saldo = validarEntero("INGRESE EL SALDO VIRTUAL QUE DISPONE:", 0);
            Cliente cliente = new Cliente(rut, nombre, email, password, "asdf",direccion, saldo);
            GestorSQlite.insertarRegistro("Cliente", GestorObjeto.obtenerAtributosObjeto(cliente));
        }
        if(usuario instanceof Admin){
            Admin admin = new Admin(rut, nombre, email,password, "", "asdf", "Adminaistrador de productos", GestorUsuarios.obtenerFechaActual());
            GestorSQlite.insertarRegistro("Admin", GestorObjeto.obtenerAtributosObjeto(admin));
        }
        System.out.println("USUARIO" + nombre.toUpperCase() + " CREADO CON EXITO !");
        GestorUsuarios.addUsuario(usuario);
        return usuario;
    }
    private static String menuValidarRut(){
        System.out.println("INGRESE SU RUT:");
        String rut = scanner.nextLine();
        if(GestorUsuarios.validarRut(rut)==false) {
            System.out.println("EL RUT INGRESADO ES INVALIDO!, REINTENTE");
            menuValidarRut();
        }
        return rut;
    }
}

