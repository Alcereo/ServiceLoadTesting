import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SampleServlet extends javax.servlet.http.HttpServlet {

    final private static MetricRegistry metrics = new MetricRegistry();
    private final Meter requests = metrics.meter("requests");

    final static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();

    final static JmxReporter sJMXreporter = JmxReporter.forRegistry(metrics).build();
    static {
        reporter.start(2, TimeUnit.SECONDS);
        sJMXreporter.start();
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        requests.mark();

        response.setStatus(200);
    }
}
