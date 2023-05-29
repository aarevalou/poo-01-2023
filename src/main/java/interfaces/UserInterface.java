package interfaces;

import models.Cliente;
import models.Pedido;
import models.Producto;
import models.UnidadCarrito;

import java.util.ArrayList;

public interface UserInterface {

    public void mostrarMenuPrincipal(ArrayList<Producto> productos);

    public void mostrarMenuDetalleProducto(Producto producto);

    public void mostrarMenuDetallePedido(Pedido pedido);

    public void mostrarMenuUsuario();

    public void mostrarMenuCarrito(ArrayList<UnidadCarrito> carrito);

    public void mostrarMenuPago(Pedido pedido);


    // MENUS PARA EL ADMIN
   /*
    public void mostrarMenuEditarProducto(Producto producto);

    public void mostrarMenuEditarCategorias(String[] categorias);
    public void mostrarMenuEditarCliente(Cliente cliente);*/
}
