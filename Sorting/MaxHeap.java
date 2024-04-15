package aed;

public class MaxHeap{
    Router[] heap;
    int tamano;

    // Clase interna para representar una tupla de dos enteros

    public MaxHeap(int tamano) {
        this.tamano = tamano;
        this.heap = new Router[tamano];
    }

    public void heapify() {
        int IndiceDeComienzo = (tamano / 2) - 1;

        for (int i = IndiceDeComienzo; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    private void heapifyDown(int i) {
        int HijoIzq = 2 * i + 1;
        int HijoDer = 2 * i + 2;
        int padre = i;

        if (HijoIzq < tamano && heap[HijoIzq].getTrafico() > heap[padre].getTrafico() ) {
            padre = HijoIzq;
        }

        if (HijoDer < tamano && heap[HijoDer].getTrafico() > heap[padre].getTrafico()) {
            padre = HijoDer;
        }

        if (padre != i) {
            swap(i, padre);
            heapifyDown(padre);
        }
    }

    private void swap(int i, int j) {
        Router copia = heap[i];
        heap[i] = heap[j];
        heap[j] = copia;
    }

    public Router proximo(){
        return heap[0];
    }

    public void encolar(Router elem){
        heap[tamano] = elem;
        tamano++;
        
        heapify();
    }

    public void desencolarMax(){
        heap[0] = heap[tamano - 1];
        tamano--;
        heapify();
    }

}
