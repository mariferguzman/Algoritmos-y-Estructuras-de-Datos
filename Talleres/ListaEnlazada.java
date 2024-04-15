package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private Nodo primero;
    private Nodo ultimo;
    private int size; 

    private class Nodo {
        // Completar
        T valor;
        Nodo sig;
        Nodo ant;
        Nodo(T v){valor = v; sig = null; ant = null;}
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        size = 0;
    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if(size == 0){
            primero = nuevo;
        } else{
            if(size == 1){
                nuevo.sig = primero;
                ultimo = nuevo.sig;
                primero = nuevo;
            } else {
                primero.ant = nuevo;
                nuevo.sig = primero;
                primero = nuevo;
            }
        }
        size++;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if(size == 0) {
            //caso base
            primero  = nuevo;
            primero.sig = null;
            primero.ant = null;            
        } else {
            if(size == 1){
                nuevo.ant = primero;
                ultimo = nuevo;
                primero.sig = ultimo;
            } else {
                ultimo.sig = nuevo;
                nuevo.ant = ultimo;
                ultimo = nuevo;
            }

        }
        size++;
    
    }

    public T obtener(int i) {
        Nodo actual = primero;
        for(int j = 0; j < i; j++){
            actual = actual.sig;
        }
        return actual.valor;

    }

    public void eliminar(int i) {

        Nodo actual = primero;
        for(int j = 0; j < i; j++){
            actual = actual.sig;
        }
        if(size == 1){
            primero = null;
        } else {
            if(actual.ant == null){
            primero = actual.sig;
            primero.ant = null;
            } else {
                if(actual.sig == null){
                   ultimo = actual.ant;
                   ultimo.sig = null;

                } else {
                   actual.sig.ant = actual.ant;
                   actual.ant.sig = actual.sig;
                }
           }
        }
        
        size--;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for(int j = 0; j < indice; j++){
            actual = actual.sig;
        }
        actual.valor = elem;

    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<T>();
        int newSize = 0;
        Nodo actual = primero;
        while(actual != null){
            nuevaLista.agregarAtras(actual.valor);
            newSize++;
            actual = actual.sig;
        }
        nuevaLista.size = newSize;
        return nuevaLista;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        ListaEnlazada<T> nuevaLista = lista.copiar();
        primero = nuevaLista.primero;
        ultimo = nuevaLista.ultimo;
        size = nuevaLista.size;
    }
    
    @Override
    public String toString() {
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("[");
        sbuffer.append(primero.valor);
        while(primero.sig != null){
            sbuffer.append(", ");
            sbuffer.append(primero.sig.valor);
            primero = primero.sig;
        }
        sbuffer.append("]");
        return sbuffer.toString();

    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        int it;
        ListaIterador(){ it = 0;  }

        public boolean haySiguiente() {
            return it < size;
        }
        
        public boolean hayAnterior() {
	        return it > 0;
        }

        public T siguiente() {
	        int i = it;
            it++;
            return obtener(i);
        }
        

        public T anterior() {
            it--;
            return obtener(it);
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}
