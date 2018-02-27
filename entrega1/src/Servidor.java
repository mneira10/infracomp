public class Servidor implements Runnable {

    Buffer bf;
    int id;

    public Servidor(Buffer bf,int id) {
        this.bf = bf;
        this.id = id;
    }

    @Override
    public void run() {

        //Los threads servidor deben correr mientras el Buffer tenga clientes pendientes.
        while(!bf.acabado()){
            Mensaje temp = bf.removerMensaje();

            //Si no logra obtener un mensaje para responder:
            if(temp==null){

                //Se duerme sobre el Buffer, en espera de que un Cliente lo despierte al agregar exitosamente un mensaje:
                synchronized (bf){
                    try {
                        System.out.println("Servidor: " + id+ " durmiendo");

                        bf.wait();
                        System.out.println("Servidor: " + id+ " despierto");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            //Si sí, lo responde y notifica al Cliente correspondiente:
            else{
                procesar(temp);
                System.out.println("Servidor "+ id + " procesando mensaje...");
                synchronized (temp){
                    temp.notifyAll();
                }
            }
        }

        System.out.println("Servidor "+ id +" acabo");
    }

    /**
     * Responder el mensaje, consiste en incrementar su contenido en 1:
     * @param msg
     */
    public void procesar(Mensaje msg){
        msg.setRespuesta(msg.getContenido()+1);
    }
}
