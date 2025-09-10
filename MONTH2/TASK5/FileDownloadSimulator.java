class FileDownload extends Thread {
    private String fileName;
    private int fileSize;

    public FileDownload(String fileName, int fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    @Override
    public void run() {
        System.out.println("Starting download: " + fileName);

        for (int i = 1; i <= fileSize; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(fileName + " downloaded " + i + " MB");
        }

        System.out.println("✅ Download completed: " + fileName);
    }
}

public class FileDownloadSimulator {
    public static void main(String[] args) throws InterruptedException {
        FileDownload file1 = new FileDownload("Video.mp4", 5);
        FileDownload file2 = new FileDownload("Song.mp3", 3);
        FileDownload file3 = new FileDownload("Document.pdf", 4);

        file1.start();
        file2.start();
        file3.start();

        file1.join();
        file2.join();
        file3.join();

        System.out.println("\n🎉 All downloads finished successfully!");
    }
}
