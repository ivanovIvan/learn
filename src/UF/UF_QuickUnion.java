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
public class UF_QuickUnion implements UF_API{
    protected int[] innerArray;
    protected int count;
    
    public UF_QuickUnion(int n) {
        innerArray = new int[n];
        for (int i=0; i<n;i++) innerArray[i] = i;
        count = n;
    }
    
    private void validate(int p) {
        if (p>=innerArray.length) throw new IndexOutOfBoundsException("Index out of Bounds");
    }
        
    @Override
    public void union(int p, int q) {
        validate(p);
        validate(q);
        if (!connected(p, q)) {
            int root_q = find(q);
            innerArray[root_q] = innerArray[find(p)];
            count--;
        }
    }

    @Override
    public int find(int p) {
        int i = p;
        while(innerArray[i]!=i) {
            i = innerArray[i];
        }
        return i;
    }

    @Override
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return find(p)==find(q);
    }

    @Override
    public int count() {
        return count;
    }
    
}
