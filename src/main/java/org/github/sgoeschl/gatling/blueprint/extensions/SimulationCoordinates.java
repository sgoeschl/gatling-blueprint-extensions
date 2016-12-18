package org.github.sgoeschl.gatling.blueprint.extensions;

import org.github.sgoeschl.gatling.blueprint.extensions.utils.Validate;

import java.util.Objects;
import java.util.Properties;

/**
 * Captures the coordinates of a Gatling simulation (test).
 */
public class SimulationCoordinates {

    private static final int NR_OF_STRING_PARTS = 4;

    private final String application;
    private final String tenant;
    private final String site;
    private final String scope;

    public static SimulationCoordinates from(Object object) {
        Validate.notNull(object, "object");

        return from(getClassName(object), System.getProperties());
    }

    public static SimulationCoordinates from(Object object, Properties properties) {
        Validate.notNull(object, "object");
        Validate.notNull(properties, "properties");

        return from(getClassName(object), properties);
    }

    public SimulationCoordinates(String application, String tenant, String site, String scope) {
        Validate.notEmpty(application, "application");
        Validate.notEmpty(tenant, "tenant");
        Validate.notEmpty(site, "site");
        Validate.notEmpty(scope, "scope");

        this.application = normalize(application);
        this.tenant = normalize(tenant);
        this.site = normalize(site);
        this.scope = normalize(scope);
    }

    public String getApplication() {
        return application;
    }

    public String getTenant() {
        return tenant;
    }

    public String getSite() {
        return site;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "SimulationCoordinates{" +
                "application='" + getApplication() + '\'' +
                ", tenant='" + getTenant() + '\'' +
                ", site='" + getSite() + '\'' +
                ", scope='" + getScope() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final SimulationCoordinates that = (SimulationCoordinates) o;
        return Objects.equals(application, that.application) &&
                Objects.equals(tenant, that.tenant) &&
                Objects.equals(site, that.site) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application, tenant, site, scope);
    }

    private static SimulationCoordinates from(String simulationClass, Properties properties) {
        final String[] parts = simulationClass.split("\\.");

        Validate.isTrue(parts.length == NR_OF_STRING_PARTS, "Expecting exactly four parts: " + simulationClass);

        final String application = parts[0];
        final String tenant = parts[1];
        final String scenario = parts[2];
        final String site = getSiteFrom(properties);
        return new SimulationCoordinates(application, tenant, site, scenario);
    }

    private static String getClassName(Object object) {
        return object instanceof String ? object.toString() : object.getClass().getName();
    }

    private static String getSiteFrom(Properties properties) {
        return properties.getProperty("site", "local");
    }

    private static String normalize(String value) {
        Validate.notEmpty(value, "value");

        return value.toLowerCase().trim();
    }
}
