package models;
import interfaces.UserInterface;

import java.awt.print.PrinterIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IConsola implements UserInterface {

    private static Scanner scanner = new Scanner(System.in);
    @Override
    public void mostrarMenuPrincipal(ArrayList<Producto> productos) {
        int selec;
        ArrayList<Producto> p = productos;

        mostrarTitulo("BIENVENIDO A LA TIENDA HARD-MUSIC !");
        selec = validarEntero("SELECCIONE UNA OPCIÓN...\n1) Ver todos los productos\n2) Buscar producto por nombre\n3) Buscar productos por categoria\n4) Ver carrito", 4);

        switch (selec){
            case 1:
                mostrarProductos(p);
                break;
            case 2:
                String busqueda="";
                busqueda = validarTexto("INGRESE EL NOMBRE DEL PRODUCTO:");
                p = GestorProductos.obtenerProductosPorBusqueda(productos, busqueda);
                if (p.isEmpty()){
                    System.out.println();
                    System.out.println("NO SE HAN ENCONTRADO COINCIDENCIAS...");
                    mostrarMenuPrincipal(productos);
                    break;
                }
                mostrarProductos(p);
                break;
            case 3:
                int categoria;
                System.out.println("LAS CATEGORIAS SON LAS SIGUIENTES:");
                System.out.println();
                for (int i = 0; i < Principal.getCategorias().length; i++) {
                    System.out.println((i+1) + ") " + Principal.getCategorias()[i]);
                }
                categoria = validarEntero("SELECCIONE UNA CATEGORIA:",Principal.getCategorias().length);
                p = GestorProductos.obtenerProductosPorCategoria(productos, categoria-1);
                mostrarProductos(p);
                break;

            case 4:
                mostrarMenuCarrito(Principal.listaCarrito);
                break;
            default:
                mostrarInvalido();
                mostrarMenuPrincipal(productos);
        }

        selec = validarEntero("PARA VER LOS DETALLES DE UN PRODUCTO, SELECCIONE SU ÍNDICE...", productos.size());
        mostrarMenuDetalleProducto(p.get(selec-1), Principal.getCliente());
    }

    @Override
    public void mostrarMenuDetalleProducto(Producto producto, Cliente cliente) {

        int input, cantidad;
        //Producto producto;
        //producto = Principal.obtenerProductoPorIndice(Principal.productos, indice);

        System.out.println();
        System.out.println("DETALLES DEL PRODUCTO: ");
        System.out.println();

        ArrayList<String> claves = new ArrayList<>();
        claves.addAll(producto.getAtributos().keySet());

        System.out.println(" - " + "Marca: " + producto.getMarca());
        System.out.println(" - " + "Modelo: " + producto.getModelo());

        for (int i = 0; i < claves.size(); i++) {
            String clave = claves.get(i);
            Object valor = producto.getAtributos().get(clave);
            System.out.println(" - " + clave + ": " + valor);
        }
        System.out.println();
        System.out.println(" - " + "Disponibilidad: " + producto.getStock());

        /*String[] specs = descripcion.split(",");

        for (int i = 0; i < specs.length; i++) {
            System.out.println(" -" + specs[i]);
        }
         */
        System.out.println();
        input = validarEntero("SELECCIONE UNA OPCIÓN: \n1) Para agregar al carrito \n2) Para comprar \n3) Para volver\n", 3);

        if (input==1) {
            cantidad = validarEntero("INGRESE LA CANTIDAD: ", 0);
            if(cantidad <= producto.getStock()){
                if(cliente.getListaCarrito().contains(producto)) {
                    int index = cliente.getListaCarrito().indexOf(producto);
                    cliente.getListaCarrito().get(index).setCantidad(cantidad);

                    System.out.println("Se ha actualizado la cantidad del producto en el carrito!");
                    mostrarMenuDetalleProducto(producto, cliente);
                }
                else{
                    UnidadCarrito unidadCarrito = new UnidadCarrito(cliente, producto, cantidad, producto.getPrecio());
                    Principal.listaCarrito.add(unidadCarrito);

                    System.out.println("Se ha agregado el producto al carrito!");
                    mostrarMenuDetalleProducto(producto, cliente);
                }
            }
            else{
                System.out.println("NO HAY SUFICIENTE STOCK DISPONIBLE!, VERIFIQUE LA DISPONIBILIDAD");
                mostrarMenuDetalleProducto(producto, cliente);
            }
        }

        if (input==2) {
            cantidad = validarEntero("INGRESE LA CANTIDAD: ",0);
            if((cantidad <= producto.getStock()) && (cliente.getSaldo() >= (producto.getPrecio()*cantidad))) {

                producto.setStock(producto.getStock() - cantidad);
                cliente.setSaldo(cliente.getSaldo() - (producto.getPrecio() * cantidad));
                Pedido p = cliente.crearPedido();
                p.setCliente(Principal.getCliente());
                p.addUnidadCarrito(new UnidadCarrito(cliente, producto, cantidad, producto.getPrecio()));
                p.setMetodoPago("Transfencia");

                mostrarMenuPago(p);

            } else if (cantidad > producto.getStock()) {
                System.out.println("NO HAY SUFICIENTE STOCK DISPONIBLE!, VERIFIQUE LA DISPONIBILIDAD");
                mostrarMenuDetalleProducto(producto, cliente);

            } else if (cliente.getSaldo() < (producto.getPrecio()*cantidad)) {
                System.out.println("NO HAY SUFICIENTE SALDO DISPONIBLE!, VERIFIQUE SU SALDO");
                mostrarMenuDetalleProducto(producto, cliente);
            }
        }

        if (input==3) {
            mostrarMenuPrincipal(Principal.getProductos());
        }

    }

    @Override
    public void mostrarMenuDetallePedido(Pedido pedido) {

        System.out.println("------------ DETALLE FACTURA DE COMPRA -----------");
        System.out.println("Productos adquiridos:");

        for (int i = 0; i < pedido.getListaProductos().size(); i++) {
            String nombre = pedido.getListaProductos().get(i).getProducto().getMarca() + " " + pedido.getListaProductos().get(i).getProducto().getModelo();
            System.out.println(nombre);
        }

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

    @Override
    public void mostrarMenuUsuario(Cliente cliente) {

        System.out.println("DATOS DEL USUARIO:");
        System.out.println("Nombre: " + cliente.getNonbre());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Dirección: " + cliente.getDireccionDespacho());
        System.out.println();
        System.out.println("SELECCIONE ALGUNA OPCIÓN:");
        System.out.println("1) Modificar datos, 2) Cambiar contraseña, 3) Salir");

        int selec;
        selec = scanner.nextInt();

        String nombre, email, direccion, password;

        if (selec==1){

            nombre = validarTexto("INGRESE SU NOMBRE");
            email = validarTexto("INGRESE SU EMAIL");
            direccion = validarTexto("INGRESE SU DIRECCIÓN");
            password = validarTexto("*INGRESE SU CONTRASEÑA PARA CONFIRMAR:");

            if(cliente.validadPassword(password))
            {
                cliente.setNonbre(nombre);
                cliente.setNonbre(email);
                cliente.setNonbre(direccion);
                System.out.println("DATOS DEL USUARIO MODIFICADOS CON EXITO!");
            }
            else{
                System.out.println("ERROR, LA CONTRASEÑA ES INCORRECTA!");
                mostrarMenuUsuario(cliente);
            }
        }

        if (selec==2){

            password = validarTexto("INGRESE SU CONTRASEÑA ANTERIOR:");
            if(cliente.validadPassword(password))
            {
                String password1, password2;
                password1 = validarTexto("INGRESE SU NUEVA CONTRASEÑA:");
                password2 = validarTexto("VUELVA A INGRESARLA PARA CONFIRMAR:");

                if(password1==password2){
                    cliente.setPassword(password1);
                    System.out.println("SU CONTRASEÑA HA SIDO CAMBIADA CON EXITO!");
                }
                else{
                    System.out.println("LAS CONTRASEÑAS INGRESADAS NO COINCIDEN!...");
                    mostrarMenuUsuario(cliente);
                }

            }
            else{
                System.out.println("ERROR, LA CONTRASEÑA ES INCORRECTA");
            }
        }

        if (selec==3){
            mostrarMenuPrincipal(Principal.getProductos());
        }
    }

    @Override
    public void mostrarMenuCarrito(ArrayList<UnidadCarrito> carrito) {

        int total=0;
        int selec;

        String borde  = "+---------------------------+------------+-------------------+%n";
        String borde2 = "+------------------------------------------------------------+%n";
        String formato = "| %-25s | %-10s | %-17s |%n";
        String formato2 = "| %-58s |%n";

        System.out.println();
        System.out.println("CARRITO:");
        System.out.format(borde, "");
        System.out.format(formato, "PRODUCTO : ", "CANTIDAD :", "PRECIO UNITARIO :");

        for (int i = 0; i < carrito.size(); i++) {
            System.out.format(borde);
            System.out.format(formato, (i+1) + ") " + carrito.get(i).getProducto().getMarca() + " " + carrito.get(i).getProducto().getModelo(), carrito.get(i).getCantidad(), carrito.get(i).getPrecioUnitario());
            total += carrito.get(i).getPrecioUnitario();
        }
        System.out.format(borde, "");
        System.out.format(formato2, "SUB TOTAL : $" + total);
        System.out.format(borde2, "");

        System.out.println();

        if(carrito.size() > 0){
            selec = validarEntero("SELECCIONE ALGUNA OPCIÓN: \n1) Proceder al pago, 2) Modificar artículo, 3) Vaciar carrito, 4) Salir", 4);
        }
        else{
            selec=0;
            System.out.println("EL CARRITO ESTÁ VACIO, OPRIMA CUALQUIER TECLA PARA SALIR...");
            scanner.nextLine();
            mostrarMenuPrincipal(Principal.getProductos());
        }

        if(selec==1){

            if(Principal.getCliente().getSaldo() >= total) {

                for (int i = 0; i < carrito.size(); i++) {
                    carrito.get(i).getProducto().setStock(carrito.get(i).getProducto().getStock() - 1);
                }
                Principal.getCliente().setSaldo(Principal.getCliente().getSaldo() - total);
                Pedido p = new Pedido();
                p = Principal.getCliente().crearPedido();
                p.setCliente(Principal.getCliente());
                p.setListaProductos(Principal.listaCarrito);
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
                System.out.println((i+1) + ") " + carrito.get(i).getProducto().getMarca() + " " + carrito.get(i).getProducto().getModelo());
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

            mostrarMenuPrincipal(Principal.getProductos());
        }

    }

    @Override
    public void mostrarMenuPago(Pedido pedido) {

        int input;

        String borde = "+----------------------+----------------------------------+%n";
        String borde2 = "+------------------------------------------------------------+%n";
        String formato = "| %-20s | %-32s |%n";
        String formato2 = "| %-58s |%n";

        System.out.println();
        mostrarTitulo("PROCEDIMIENTO PEDIDO");
        System.out.println();
        System.out.println("  DATOS DEL CLIENTE:");

        System.out.format(borde);
        System.out.format(formato, "Nombre : ", pedido.getCliente().getNonbre());
        System.out.format(borde);
        System.out.format(formato, "Email : ", pedido.getCliente().getEmail());
        System.out.format(borde);
        System.out.format(formato, "Fecha creación : ", pedido.getFechaCreacion());
        System.out.format(borde);
        System.out.format(formato, "Metodo de pago : ", pedido.getMetodoPago());
        System.out.format(borde, "");

        System.out.println();
        System.out.println("RESUMEN DEL PEDIDO:");

        int total=0;
        for (int i = 0; i < pedido.getListaProductos().size() ; i++) {
            String marca = pedido.getListaProductos().get(i).getProducto().getMarca();
            String modelo = pedido.getListaProductos().get(i).getProducto().getModelo();
            int precio = pedido.getListaProductos().get(i).getProducto().getPrecio();

            System.out.println("- " + marca + " " + modelo + "           " + precio);
            total += pedido.getListaProductos().get(i).getPrecioUnitario();
        }

        System.out.println("Sub-total : " + total);
        System.out.println();


        System.out.println("  DATOS DEL DESPACHO:");

        System.out.format(borde);
        System.out.format(formato, "Dirección : ", pedido.getDespacho().getDireccion());
        System.out.format(borde);
        System.out.format(formato, "Región : ", pedido.getDespacho().getRegion());
        System.out.format(borde);
        System.out.format(formato, "Comuna : ", pedido.getDespacho().getComuna());
        System.out.format(borde);
        System.out.format(formato, "Costo despacho : ", pedido.getDespacho().getCostoDespacho());
        System.out.format(borde);
        System.out.format(formato, "Dirección : ", pedido.getDespacho().getDireccion());

        total += pedido.getDespacho().getCostoDespacho();

        System.out.format(borde, "");
        System.out.format(formato2, "TOTAL : $" + total);
        System.out.format(borde2, "");


        input = validarEntero("1) PARA CONFIRMAR COMPRA, 2) PARA CANCELAR", 2);

        switch (input){
            case 1:
                System.out.println();
                System.out.println("¡GRACIAS POR SU COMPRA!");
                pedido.generarFactura();
                continuar();
                break;
            case 2:
                mostrarMenuPrincipal(Principal.getProductos());
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
            System.out.format(formato, (i+1) + ") " + productos.get(i).getMarca(), productos.get(i).getModelo(), productos.get(i).getPrecio());
        }
        System.out.format(borde, "");
    }

    public static void mostrarTitulo(String titulo){
        System.out.println();
        System.out.println("---------- " + titulo + " ----------");
    }

   /* public static String validarEntrada(String mensajeEntrada){

        String input="";
        try {
            System.out.println();
            System.out.print(mensajeEntrada);
            input = scanner.nextLine();

        } catch (Exception e) {
            System.out.println("ENTRADA INVALIDA, REINTENTE...");
            validarEntrada(mensajeEntrada);
        }

        return input;
    }*/

    public static void mostrarInvalido(){
        System.out.println("ENTRADA INVALIDA, POR FAVOR REINTENTE...");
    }

    public void continuar() {
        int input;
        System.out.println();
        input = validarEntero("DESEA SEGUIR EN LA TIENDA?\n1) SI, 2) NO", 2);

        switch (input) {
            case 1:
                mostrarMenuPrincipal(Principal.getProductos());
                break;
            case 2:
                break;
            default:
                mostrarInvalido();
                continuar();
        }
    }

    public void mostrarMenuPrincipalAdmin(){
        int selec;
        mostrarTitulo("MENU PRINCIPAL");
        selec = validarEntero("SELECCIONE UN ACCIÓN:\n1) Gestionar productos, 2) Gestionar pedidos, 3) Gestionar clientes, ", 3);

        if(selec==1) {
            System.out.println();
            mostrarProductos(Principal.productos);
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

    public void mostrarMenuAgregarProducto(){

        System.out.println();
        System.out.println("MARCAS:");
        for (int i = 0; i < Principal.getMarcas().length; i++) {
            System.out.println((i+1) + ") " + Principal.getMarcas()[i]);
        }
        System.out.println();
        int marca_id = validarEntero("SELECCIONE LA MARCA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", Principal.getMarcas().length);
        String marca = Principal.getMarcas()[marca_id-1];
        String modelo = validarTexto("INGRESE EL MODELO DEL PRODUCTO:");

        System.out.println();
        System.out.println("CATEGORÍAS:");
        for (int i = 0; i < Principal.getCategorias().length; i++) {
            System.out.println((i+1) + ") " + Principal.getCategorias()[i]);
        }
        System.out.println();
        int categoria = validarEntero("SELECCIONE LA CATEGORIA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", Principal.getMarcas().length);
        int stock = validarEntero("INGRESE EL STOCK DEL PRODUCTO:", 0);
        int precio = validarEntero("INGRESE EL PRECIO DEL PRODUCTO:", 0);

        Producto p = new Producto();

        p.setMarca(marca);
        p.setModelo(modelo);
        p.setCategoria(categoria);
        p.setStock(stock);
        p.setPrecio(precio);

        int input;
        for (int i = 0; i < 10; i++) {
            System.out.println();
            System.out.println("ATRIBUTOS EXTRAS:");
            input = validarEntero("1) Para agregar un atributo, 2) Para terminar", 2);

            if(input==1){
                String clave = validarTexto("INGRESE EL NOMBRE DEL ATRIBUTO:");
                String valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");

                p.addSpec(clave, valor);

                System.out.println("ATRIBUTO AGREGADO!");
            }
            if(input==2){ break; }
        }

        System.out.println();
        System.out.println("HA AGREGADO EL PRODUCTO CON EXITO!");
        System.out.println();

        Principal.getProductos().add(p);
        mostrarMenuPrincipalAdmin();
    }

    public void mostrarMenuModificarProducto(){
        int indice;
        indice = validarEntero("INGRESE EL ÍNDICE DEL PRODUCTO QUE DECEA MODIFICAR", Principal.getProductos().size());

        System.out.println();
        System.out.println("MARCAS:");
        for (int i = 0; i < Principal.getMarcas().length; i++) {
            System.out.println((i+1) + ") " + Principal.getMarcas()[i]);
        }
        System.out.println();
        int marca_id = validarEntero("SELECCIONE LA MARCA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", Principal.getMarcas().length);
        String marca = Principal.getMarcas()[marca_id-1];
        String modelo = validarTexto("INGRESE EL MODELO DEL PRODUCTO:");

        System.out.println();
        System.out.println("CATEGORÍAS:");
        for (int i = 0; i < Principal.getCategorias().length; i++) {
            System.out.println((i+1) + ") " + Principal.getCategorias()[i]);
        }
        System.out.println();
        int categoria = validarEntero("SELECCIONE LA CATEGORIA DEL PRODUCTO SEGÚN LA LISTA ANTERIOR:", Principal.getMarcas().length);
        int stock = validarEntero("INGRESE EL STOCK DEL PRODUCTO:", 0);
        int precio = validarEntero("INGRESE EL PRECIO DEL PRODUCTO:", 0);

        Producto p = Principal.getProductos().get(indice-1);

        p.setMarca(marca);
        p.setModelo(modelo);
        p.setCategoria(categoria);
        p.setStock(stock);
        p.setPrecio(precio);

        for (int i = 0; i < 10; i++) {
            System.out.println();
            System.out.println("ATRIBUTOS EXTRAS:");
            indice = validarEntero("1) Para agregar un atributo, 2) Para editar un atributo, 3) Eliminar un atributo, 4) Para terminar", 2);

            if(indice==1){
                String clave = validarTexto("INGRESE EL NOMBRE DEL ATRIBUTO:");
                String valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");

                p.addSpec(clave, valor);

                System.out.println("ATRIBUTO AGREGADO!");
            }

            if(indice==2){
                System.out.println("ATRIBUTOS EXTRAS DEL PRODUCTO:");
                for (int j = 0; j < p.getAtributos().size(); j++) {
                    System.out.println((i+1) + ") " + p.getAtributos().get(i));
                }
                int keyAtributo  = validarEntero("INGRESE EL INDICE DEL ATRIBUTO QUE DECEA MODIFICAR:", 0);
                String clave = validarTexto("INGRESE EL NOMBRE DEL ATRIBUTO:");
                String valor = validarTexto("INGRESE EL VALOR DEL ATRIBUTO:");
                HashMap edit = new HashMap<>();
                edit.put(clave, valor);
                p.getAtributos().get(p.getAtributos().get(keyAtributo)).equals(edit);
            }

            if(indice==3){
                System.out.println("ATRIBUTOS EXTRAS DEL PRODUCTO:");
                for (int j = 0; j < p.getAtributos().size(); j++) {
                    System.out.println((i+1) + ") " + p.getAtributos().get(i));
                }
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

        Principal.getProductos().get(indice-1).equals(p);
        mostrarMenuPrincipalAdmin();
    }

    public void mostrarMenuEliminarProducto(){

    }

    public void mostrarMenuModificarPedido(Pedido pedido){

    }

    public void mostrarMenuModificarCliente(Cliente cliente){

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

    public void inicio(int opcion) {
	if(opcion == 1) {
	    mostrarMenuPrincipal(Principal.getProductos());
	} else {
	    mostrarMenuPrincipalAdmin();
	}
    }
}

