import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Cliente implements Runnable {

    Queue<Mensaje> mensajes;
    int numMensajes;
    int id;
    Buffer bf;

    public Cliente(int numMensajes, int id, Buffer bf) {
        this.numMensajes = numMensajes;
        this.id = id;
        this.bf = bf;

        mensajes = new LinkedList<>();
        for(int i = 0 ; i< numMensajes ; i++){
            mensajes.add(nuevoMensaje());
        }

    }

    private Mensaje nuevoMensaje(){
        return new Mensaje(1);
    }

    @Override
    public void run() {
        while(!mensajes.isEmpty()){
            Mensaje temp = mensajes.peek();

                synchronized (temp){
                    try {
                        if(bf.agregarMensaje(temp)) {
                            mensajes.remove();
                            System.out.println("Cliente: " + id + " durmiendo");
                            temp.wait();
                            System.out.println("Cliente: " + id + " despierto");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


//                numMensajes--;
            }

        }
        bf.notificar();
        System.out.println("Cliente " + id + " acabo");
    }
}

