package net.kettlemc.titan.config;

public class TextUtils {

    // Inspired by Bukkit's ChatColor methods
    public static String color(String toColor) {
        char[] array = toColor.toCharArray();
        for (int index = 0; index < array.length - 1; index++) {
            if (array[index] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(array[index + 1]) > -1) {
                array[index] = '\u00a7';
                array[index + 1] = Character.toLowerCase(array[index + 1]);
            }
        }
        return new String(array);

    }

}
