/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UF;

/**
 *
 * @author dav
 */
public class UF_WeightedQuickUnion extends UF_QuickUnion{

    private int[] weight;
    
    public UF_WeightedQuickUnion(int n) {
        super(n);
        weight = new int[n];
        for (int i = 0; i<n;i++){
            weight[i] = 1;
        }
    }

    private void validate(int p) {
        if (p>=innerArray.length) throw new IndexOutOfBoundsException("Index out of Bounds");
    }
        
    @Override
    public void union(int p, int q) {
        validate(p);
        validate(q);
        if (!connected(p, q)) {
            int rootTo, rootWho;
            if (getWeight(q)<=getWeight(p)) {
                rootTo = find(p);
                rootWho = find(q);
            } else {
                rootTo = find(q);
                rootWho = find(p);
            }
            innerArray[rootWho] = innerArray[rootTo];
            weight[rootTo] = weight[rootTo] + weight[rootWho];
            weight[rootWho] = 0;
            count--;
        }        
    }
    
    
    private int getWeight(int p) {
        return weight[find(p)];
    }

    @Override
    public int find(int p) {
        while (innerArray[p]!=p){
            p = innerArray[p];
        }
        return p; //To change body of generated methods, choose Tools | Templates.
    }
    
}
