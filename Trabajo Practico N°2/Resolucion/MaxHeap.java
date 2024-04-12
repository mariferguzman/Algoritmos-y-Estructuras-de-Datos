package aed;

public class MaxHeap implements ColaDePrioridad{
    private Nodo raiz;
    private int size;
    private Nodo[] accederRapido;
    
    private class Nodo {
        int idPartido;
        int votos;
        int indice;
        Nodo izq;
        Nodo der;
        public Nodo(int idPartido, int votos, int indice) {
            this.idPartido = idPartido;                               // O(1)
            this.votos = votos;                                       // O(1)
            this.indice = indice;                                     // O(1)
        }
    }
    public MaxHeap(int tamaño) {                                            // O(1)
        this.accederRapido = new Nodo[tamaño];                              // O(1)
        this.raiz = null;                                                   // O(1)
        this.size = 0;                                                      // O(1)
    }
    public MaxHeap(int[] elementos) {                                       // O(n)
        this.size = elementos.length;                                       // O(1)
        this.accederRapido = new Nodo[elementos.length];                    // O(1)

        if(elementos.length > 0) {                                          // O(1)
            this.raiz = new Nodo(0, elementos[0], 0);      // O(1)
            accederRapido[0] = this.raiz;                                   // O(1)
        
    
            for(int i = 1; i < this.size; i ++) {                                                                                       // O(n)
                Nodo padre = nodoPadre(i),                                                                                              // O(1)
                nuevoNodo = new Nodo(i, elementos[i], i);                                                                               // O(1)

                if(padre.izq == null)                                                                                                   // O(1)
                    padre.izq = nuevoNodo;                                                                                              // O(1)
                else
                    padre.der = nuevoNodo;                                                                                              // O(1)
                accederRapido[i] = nuevoNodo;                                                                                           // O(1)
            }
            // Ordeno por el agoritmo de Floyd
            int ultimoNivelCompleto = ((int) log_2(this.size + 1)) - 1;                                                                 // O(1)
            while(ultimoNivelCompleto >= 0) {                                                                                           // O(log(n))
                for(int i = (int) Math.pow(2, ultimoNivelCompleto) - 1; i < (int) Math.pow(2, ultimoNivelCompleto + 1) - 1; i ++)   // O(2^(k+1) - 2^k) = O(2^(k)), k = ultimoNivelCompleto
                    heapifyDown(accederRapido[i]);                                                                                      // O(log(n))

                ultimoNivelCompleto--;                                                                                                  // O(1)
            }
        }
        else
            this.raiz = null;                                                                                                           // O(1)
    }
    // el ciclo termina dando O(Log(n)) por la justificacion vista en la clase teorica

    @Override
    public void insertar(int id, int votos) {                               // O(log(n))
        if(this.size == 0) {                                                // O(1)
            this.raiz = new Nodo(id, votos, 0);                      // O(1)
            this.accederRapido[0] = this.raiz;                              // O(1)
            this.size++;                                                    // O(1)
        }
        else{
            int indice = this.size;                                         // O(1)
            Nodo nuevoNodo = new Nodo(id, votos, indice),                   // O(1)
                    padre = nodoPadre(this.size);                           // O(1)
        
            if(padre.izq == null)                                           // O(1)
                padre.izq = nuevoNodo;                                      // O(1)
            else
                padre.der = nuevoNodo;                                      // O(1)
            
            this.accederRapido[this.size] = nuevoNodo;                      // O(1)
            heapifyUp(nuevoNodo, padre);                                    // O(log(n))
            this.size++;                                                    // O(1)
        }
    }

    private void heapifyUp(Nodo nodo, Nodo padre){
        while(padre != null && nodo.votos > padre.votos) {         // O(log(n))
            swap(nodo, padre);                                     // O(1) 
            nodo = padre;                                          // O(1) 
            padre = nodoPadre(nodo.indice);                        // O(1) 
            }
    }


    @Override
    public int desencolar() {                                               // O(log(n))
        int res = this.raiz.idPartido;                                      // O(1)
        Nodo ultimoNodo = this.accederRapido[this.size - 1];                // O(1)
        swap(this.raiz, ultimoNodo);                                        // O(1)
        
        Nodo nodoABorrar = this.accederRapido[this.size - 1],               // O(1)
             padre = nodoPadre(nodoABorrar.indice);                         // O(1)
        if(padre != null)                                                   // O(1)
            if(padre.der == nodoABorrar)                                    // O(1)
                padre.der = null;                                           // O(1)
            else 
                padre.izq = null;                                           // O(1)
        this.accederRapido[this.size - 1] = null;                           // O(1)
        nodoABorrar = null;                                                 // O(1)

        heapifyDown(this.raiz);                                             // O(log(n))
        this.size --;                                                       // O(1)
        return res;             
    }

    @Override
    public int raiz() {                 // O(1)
        return this.raiz.idPartido;     // O(1)
    }

    @Override
    public int tamaño() {          // O(1)
        return this.size;          // O(1)
    }

    @Override
    public boolean estaVacio() {        // O(1)
        return this.size == 0;          // O(1)
    }

    private void swap(Nodo a, Nodo b) {             //O(1) 
        int a_idPartido = a.idPartido;              // O(1)
        int a_votos = a.votos;                      // O(1)
        
        a.idPartido = b.idPartido;                  // O(1)
        a.votos = b.votos;                          // O(1)

        b.idPartido = a_idPartido;                  // O(1)
        b.votos = a_votos;                          // O(1)
    }
    


    private double log_2(double d) {           //O(log(n))
        return Math.log(d)/Math.log(2);
    }
    // Como las funciones estandard de java Math.log y Math.pow son O(1), supongo que las funciones log y pow son iterativas es decir un ciclo donde voy multiplicando n veces un numero m para el pow, o un ciclo que vea cuantas veces el 2 fue multiplicado.
    // Si son iterativas log_2 cuesta O(log(n)) siendo n el valor que le ingreses a la funcion. Y pow O(m) siendo m la cantidad de veces que se multiplca el valor n.

    private Nodo nodoPadre(int indice) {                        //O(1)
        Nodo res = null;                                        //O(1)
        if(indice != 0)
            res = this.accederRapido[(indice - 1) / 2];         //O(1)
        return res;
    }

    private void heapifyDown(Nodo nodo) {                                                                                                           // O(log(n))
        while((nodo.der != null && nodo.votos < maximo(nodo.der.votos, nodo.izq.votos) || nodo.izq != null && nodo.votos < nodo.izq.votos)){        // O(log(n))

            if(nodo.der != null && nodo.der.votos > nodo.izq.votos){                                                                                // O(1)
                swap(nodo, nodo.der);                                                                                                               // O(1)
                nodo = nodo.der;                                                                                                                    // O(1)
            }
            else {
                swap(nodo, nodo.izq);                                                                                                               // O(1)
                nodo = nodo.izq;                                                                                                                    // O(1)
            }
        }
    }

    private int maximo(int i, int j) {  // O(1)
        int max = i;                    // O(1)
        if(j > i)                       // O(1)
            max = j;                    // O(1)
        return max;                     // O(1)
    }
}



/*
InvRep (h: MaxHeap){
    0 <= h.size <= h.accederRapido.length
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> (( n.izq == null || n.izq.votos < n.votos) && (n.der == null || n.der.votos < n.votos))
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> 0 <= n.indice < h.size
    forall n,l: Nodo :: n,l != null &&L n,l in h.accederRapido && n != l ==> n.indice != l.indice
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> (Exists i:int :: 0 <= i < h.accederRapido.length && h.accederRapido[i] == n && n.indice = i)
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> ((n.izq != null ==> n.izq in h.accederRapido) ==> (n.der != null ==> n.der in h.accederRapido))
    forall n: Nodo :: n != null &&L n in h.accederRapido ==>(n.izq.indice = 2 * (n.indice) + 1 && n.der.indice = 2 * (n.indice) + 2) || (n.izq = null && n.der = null))
    forall n: Nodo :: ((n != null &&L n in h.accederRapido && n != raiz ==> n.padre = h.accederRapido(padre(n)) || (n != null &&L n in h.accederRapido && n = raiz ==> (n.padre = null && h.accederRapido[0] = n))) 
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> (n.izq = null ==> n.der = null)  # el arbol tiende a ser izquierdista
    forall n: Nodo :: n != null &&L n in h.accederRapido ==> -1 <= altura(n.der) - altura(n.izq) <= 1
    forall c : int :: 2^c < size ==> (forall i,j: int :: 0 <= i,j < h.size && 2^c <= i,j < 2^(c + 1) && i < j ==>L (h.accederRapido[i].der == null ==>L h.accederRapido[j].izq == null && h.accederRapido[j].der == null)(h.accederRapido[i].der != null ==>L(j < i + 1 ==> (h.accederRapido[j].izq = null && h.accederRapido[j].der == null) && j = i + 1 ==> h.accederRapido[j].der == null)))
}


aux padre(Nodo nodo) : int = if (nodo.indice % 2 == 0) then nodo.indice/2 - 1 else floor(nodo.indice/2) 
aux altura(Nodo nodo) : int = if (nodo == null) then -1 else (maximo(altura(nodo.izq), altura(nodo.der)) +1)
aux maximo(int i, int j) : int = if (i > j) then i else j fi

 */

