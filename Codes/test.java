public class test {
    public static void main(String[] args) {
        PlaneBFS p = new PlaneBFS(40);
        for(int i=0; i<100; i++)
            p.nextStep();
        try{Thread.sleep(1_000);}
        catch(Exception e){}
        while(!p.traveled())
            p.nextStep();
    }
}
