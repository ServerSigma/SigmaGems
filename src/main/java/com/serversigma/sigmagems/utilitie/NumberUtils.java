package com.serversigma.sigmagems.utilitie;

import java.text.DecimalFormat;

public class NumberUtils {

    private static final String[] NUMBER_FORMAT =
            ("k;M;B;T;Q;QQ;S;SS;OC;N;D;UN;DD;TR;QT;QN;SD;SPD;OD;" +
                    "ND;VG;UVG;DVG;TVG;QTV;QNV;SEV;SPV;OVG;NVG;TG").split(";");

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");

    private static String formatLarge(double n, int iteration) {
        double f = n / 1000.0D;
        return f < 1000 || iteration >= NUMBER_FORMAT.length - 1 ?
                DECIMAL_FORMAT.format(f) + NUMBER_FORMAT[iteration] : formatLarge(f, iteration + 1);
    }

    public static String format(double value) {
        return value < 1000 ? DECIMAL_FORMAT.format(value) : formatLarge(value, 0);
    }

    public static double parse(String value) {
        try {
            return Double.parseDouble(value.replaceAll("[^0-9]", ""));
        } catch (Exception exception) {
            return -1.0;
        }
    }

}