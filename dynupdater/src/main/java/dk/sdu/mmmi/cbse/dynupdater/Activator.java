package dk.sdu.mmmi.cbse.dynupdater;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

public class Activator implements BundleActivator {

    private BundleContext context;

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
//TODO: Implement dynupdater
//        s.schedule(dynInstaller, 20, TimeUnit.SECONDS);

    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }

    private final Runnable dynInstaller = new Runnable() {

        @Override
        public void run() {
            try {
                File folder = new File(".//plugins");
                for (File file : folder.listFiles()) {
                    if (file.getName().endsWith(".jar")) {
                        context.installBundle(file.toURI().toString());
                    }
                }
            } catch (BundleException ex) {
                System.out.println(ex);
                Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

}
