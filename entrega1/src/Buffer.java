import javafx.beans.binding.ObjectExpression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class Buffer {

    int nClientes;
    int nServidores;
    int capacidad;
    Queue<Mensaje> mensajes;
    Object lock;
//    boolean acabado;

    public Buffer( int capacidad,int nClientes, Object lock) {
        this.nClientes = nClientes;
//        this.nServidores = nServidores;
        this.capacidad = capacidad;
//        acabado = false;
        mensajes = new LinkedList<>();
        this.lock = lock;
    }

    /**
     * Método que llamará un Cliente, mandando el mensaje que quiere ser respondido.
     * @param msg
     * @return Si se pudo agregar o no.
     */
    public synchronized boolean agregarMensaje(Mensaje msg){
        if(mensajes.size()==capacidad)
            return false;
        else{
            System.out.println("Agregando mensaje...");
            mensajes.add(msg);
            this.notify();
            return true;
        }
    }

    /**
     * Retira el primer mensaje que necesita ser atendido.
     * @return El Mensaje si existe, null si no.
     */
    public synchronized Mensaje removerMensaje(){
        return mensajes.poll();
    }

    /**
     * Indica si ya se atendieron todos los clientes:
     * @return
     */
    public synchronized boolean acabado(){
        return nClientes==0;
    }

    /**
     * Notifica que hay un cliente pendiente menos. Si ya es el último, debe permitir que continúe la ejecución del Main.
     */
    public synchronized void notificar(){
        nClientes--;
//        System.out.println("Nclientes en buffer: " + nClientes);
        // Si ya no hay clientes pendientes, la ejecución del Main puede continuar:
        if(nClientes==0){
            System.out.println("Notifica a todos");
            synchronized (lock){
                lock.notifyAll();
                this.notifyAll();
     }
        }
    }
}
