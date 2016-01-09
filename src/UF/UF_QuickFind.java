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
public class UF_QuickFind implements UF_API{
    int[] innerArray;
    int count;  // count component
    
    public UF_QuickFind(int n) {
        count = n;
        innerArray = new int[n];
        for (int i = 0; i<n; i++){
            innerArray[i] = i;
        }
    }
    
    @Override
    public void union(int p, int q) {
        if (!connected(p, q)){
            // make connection, q connect to p
            int temp_group = find(p);
            for (int i=0;i<innerArray.length;i++){
                if (innerArray[i]==temp_group) innerArray[i] = innerArray[q];
            }
            count--;
        }
    }

    @Override
    public int find(int p) {
        return innerArray[p];
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p)==find(q);
    }

    @Override
    public int count() {
        return count;
    }
    
}
