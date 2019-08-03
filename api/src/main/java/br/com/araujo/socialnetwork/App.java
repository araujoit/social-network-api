package br.com.araujo.socialnetwork;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            args = new String[]{"server", "config.yml"};
        new DropwizardApp().run(args);
    }
}
