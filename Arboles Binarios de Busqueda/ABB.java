package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz;
    private int tamano;

    private class Nodo {
        // Agregar atributos privados del Nodo
        private T valor;
        private Nodo derecho;
        private Nodo izquierdo;

        // Crear Constructor del nodo
        Nodo (T valor) {
            this.valor = valor;
            this.derecho = null;
            this.izquierdo = null;
        }
    }

    public ABB() {
        raiz = null;
        tamano = 0;
    }

    public int cardinal() {
        return tamano;
    }

    public T minimo(){
        Nodo actual = raiz;
        if (tamano == 0){
            return raiz.valor;
        }
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = raiz;
        if (tamano == 0) {
            return raiz.valor;     
        }
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.valor;
    }

    public void insertar(T elem){
        if (tamano == 0) {
            raiz = new Nodo(elem);
            tamano++;
            return;
        }

        Nodo ultimoNodo = buscar_nodo(raiz, elem);

        if (ultimoNodo.valor.compareTo(elem) !=0) {
            Nodo nuevoNodo = new Nodo(elem);

            if (ultimoNodo.valor.compareTo(elem) > 0){
                ultimoNodo.izquierdo = nuevoNodo;
            } else {
                ultimoNodo.derecho = nuevoNodo;
            }

            tamano++;
        }
    }

    public Nodo buscar_nodo(Nodo raiz, T elem) {
        Nodo actual = raiz;
        Nodo anterior = null;

        while (actual != null) {
            anterior = actual;

            if (actual.valor.compareTo(elem) == 0){
                return actual;
            } else {
                if (actual.valor.compareTo(elem) > 0){
                actual = actual.izquierdo;
                } else {
                actual = actual.derecho;
                }
            }
        }
        return anterior;
    }

    public boolean pertenece(T elem){
        Nodo actual = raiz;
        
        while (actual != null){
            if (elem.compareTo(actual.valor) == 0){
                return true;
            } else {
                if (elem.compareTo(actual.valor) < 0) {
                    actual = actual.izquierdo;
                } else {
                    actual = actual.derecho;
                }
            }
        }
        return false;
    }

    public void eliminar(T elem){
        // Llamo al auxiliar para asi eliminar un elemento y actualizo el tamano
        raiz = auxiliarEliminar(raiz, elem);
        tamano--;
    }
    
    // Axuliar para eliminar un elemento del arbol binario
    private Nodo auxiliarEliminar(Nodo nodoActual, T elem) {

        if (nodoActual == null) {
            return nodoActual;
        }
        
        // Compara el elem con el valor del nodo actual
        int comparacion = elem.compareTo(nodoActual.valor);

        // Si el elem es menor que el valor del nodo actual, pasa al subarbol izq
        if (comparacion < 0) {
            nodoActual.izquierdo = auxiliarEliminar(nodoActual.izquierdo, elem);
        }
        // Si el elem es mayor que el valor del ndo actual, entonces pasa al subarbol der
        else if (comparacion > 0) {
            nodoActual.derecho = auxiliarEliminar(nodoActual.derecho, elem);
        }
        //Si el elem es igual al valor del nodo actual, se elimina
        else {
            // Si el nodo actual tiene un subarbol izq nulo, devuelve el subarbol der
            if (nodoActual.izquierdo == null) {
                return nodoActual.derecho;
            }
            // Si el nodo actual tiene un sbarbol der nulo, devuelve el subarbol izq
            else if (nodoActual.derecho == null) {
                return nodoActual.izquierdo;
            }

            nodoActual.valor = encontrarMinimo(nodoActual.derecho);

            nodoActual.derecho = auxiliarEliminar(nodoActual.derecho, nodoActual.valor);
        }
        
        return nodoActual; 

    }

    private T encontrarMinimo (Nodo nodo){
        T min = nodo.valor;
        while (nodo.izquierdo != null) {
            min = nodo.izquierdo.valor;
            nodo = nodo.izquierdo;
        }
        return min;
    }


    public String toString(){
        String arbolString = inOrder(raiz, "");
        arbolString = arbolString.substring(0, arbolString.length()-1);
        return "{" + arbolString +"}";
    }

    public String inOrder(Nodo actual, String arbolString) {
        if (actual == null) {
            return arbolString;
        }
        arbolString = inOrder(actual.izquierdo, arbolString);
        arbolString += actual.valor + ",";
        arbolString = inOrder(actual.derecho,arbolString);

        return arbolString;
    }

    private class ABB_Iterador implements Iterador<T> {
        private Stack<Nodo> pila;

        ABB_Iterador() {
            pila = new Stack<>();
            Nodo nodo = raiz;
            while (nodo != null) {
                pila.push(nodo);
                nodo = nodo.izquierdo;
            }
        }

        public boolean haySiguiente() {            
            return !pila.isEmpty();
        }
    
        public T siguiente() {

            Nodo actual = pila.pop();
            T valor = actual.valor;

            if (actual.derecho != null) {
                Nodo nodo = actual.derecho;
                while (nodo != null) {
                    pila.push(nodo);
                    nodo = nodo.izquierdo;
                }
            }
            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
