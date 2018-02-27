import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Cliente extends  Thread {

    Queue<Mensaje> mensajes;
    int numMensajes;
    int id;
    Buffer bf;
    int exitos;

    public Cliente(int numMensajes, int id, Buffer bf) {
        this.numMensajes = numMensajes;
        this.id = id;
        this.bf = bf;
        exitos =0;

        mensajes = new LinkedList<>();
        for(int i = 0 ; i< numMensajes ; i++){
            mensajes.add(nuevoMensaje());
        }

    }

    private Mensaje nuevoMensaje(){
        return new Mensaje(new Random().nextInt(10));
    }

    @Override
    public void run() {
        while(!mensajes.isEmpty()){
            Mensaje temp = mensajes.peek();

                synchronized (temp){
                    try {

                        //Intenta agregar mensaje
                        if(bf.agregarMensaje(temp)) {
                            mensajes.remove();
                            //Entregó y esperará respuesta:
                            //System.out.println("Cliente: " + id + " entregó.");
                            temp.wait();

                            //Le respondieron:
                            //System.out.println("Cliente " + id + " le respondieron.");


                            //Verificación de que la respuesta es exitosa:
                            if(temp.getRespuesta()==temp.getContenido()+1){
                                //System.out.println("Cliente: " + id + " le respondieron exitosamente.");
                                exitos++;
                            }
                        }
                        //Si no lo logra, cede procesador e intenta de nuevo:
                        else{
                            //System.out.println("Cliente id: " + id +" HIZO YIELD!! -------------------------------------------------------");
                            this.yield();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


//                numMensajes--;
            }

        }
        bf.notificar();
        //System.out.println("Cliente " + id + " acabo");
        // Verificación de que todas las respuestas fueron exitosas
//        if(numMensajes==exitos){
//            System.out.println("Cliente " + id + " acabo exitosamente: " );
//        }
//        else System.out.println("Cliente " + id + " FALLOOOOOOOOOOOOOOOOOOOO--------------------------------------------------: " );


    }
}

