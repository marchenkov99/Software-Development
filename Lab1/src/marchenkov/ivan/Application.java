package marchenkov.ivan;

public class Application {

    public static void main(String[] args){
        FileWorker worker = new FileWorker();
        worker.copy(System.getProperty("user.dir") + "/data/");
    }
}
