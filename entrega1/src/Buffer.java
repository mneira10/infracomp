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
    public synchronized Mensaje removerMensaje(){
        return mensajes.poll();
    }

    public synchronized boolean acabado(){
        return nClientes==0;
    }

    public synchronized void notificar(){
        nClientes--;
//        System.out.println("Nclientes en buffer: " + nClientes);
        if(nClientes==0){
            System.out.println("Notifica a todos");
            synchronized (lock){
                lock.notifyAll();
                this.notifyAll();
     }
        }
    }
}
