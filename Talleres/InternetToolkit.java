package aed;

public class InternetToolkit {
    public InternetToolkit() {
    }

    public Fragment[] tcpReorder(Fragment[] fragments) {
        for(int i = 1; i < fragments.length ; i++ ){
            int j = i - 1;
            Fragment elem = fragments[i];
            while(j >= 0 && fragments[j].compareTo(elem) == 1){
                fragments[j + 1] = fragments[j];
                j--;
            }
            fragments[j+1] = elem;
        }
        return fragments;
        
    }

    public Router[] kTopRouters(Router[] routers, int k, int umbral) { 
        MaxHeap HeapAct = new MaxHeap(routers.length);
        for(int i = 0; i < routers.length; i++){        
            HeapAct.heap[i] = routers[i];
        }

        HeapAct.heapify();  

        Router[] res = new Router[k];
        int contador = k;
        int pos = 0;
        for(int j = 0; j < HeapAct.heap.length; j++){
            if(HeapAct.heap[j].getTrafico() > umbral && contador > 0){
                res[pos] = HeapAct.proximo();
                HeapAct.desencolarMax();
                contador--;
                pos++;
            }
        }

        return res;
    }

    public IPv4Address[] sortIPv4(String[] ipv4) {
        IPv4Address[] res = new IPv4Address[ipv4.length];
        for(int i = 0; i < ipv4.length; i++){
            IPv4Address valor = new IPv4Address(ipv4[i]);
            res[i] = valor;
        }

        for (int i = 0; i < res.length-1; i++) {
            for (int j = 0; i < res.length - j - 1; j++) {
                for (int k = 0; k < 4; k++) {
                    if (res[j].getOctet(k) > res[j + 1].getOctet(k)) {
                        IPv4Address copia = res[j];
                        res[j] = res[j + 1];
                        res[j + 1] = copia;
                        break;
                    } 
                    if (res[j].getOctet(k) < res[j + 1].getOctet(k)) {
                        break; 
                    }
                }
            }
        }

        return res;
    }

}
