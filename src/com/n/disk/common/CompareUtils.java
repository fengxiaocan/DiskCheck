package com.n.disk.common;

public class CompareUtils {

    /**
     * a != b && a!= c && a!= d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean unequals(T compare, T... values) {
        if (compare == null) {
            if (values == null) {
                return false;
            } else {
                for (T value : values) {
                    if (value == null) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare == value) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }


    /**
     * a != b || a!= c || a!= d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean orUnequals(T compare, T... values) {
        if (compare == null) {
            if (values != null) {
                for (T value : values) {
                    if (value != null) {
                        return true;
                    }
                }
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare != value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * a == b || a== c || a== d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean orEquals(T compare, T... values) {
        if (compare == null) {
            if (values == null) {
                return true;
            } else {
                for (T value : values) {
                    if (value == null) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            if (values != null) {
                for (T value : values) {
                    if (compare.equals(value)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * a == b && a== c && a== d
     *
     * @param compare
     * @param values
     * @return
     */
    public static <T> boolean equals(T compare, T... values) {
        if (compare == null) {
            if (values != null) {
                for (T value : values) {
                    if (value != null) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            if (values != null) {
                for (T value : values) {
                    if (!compare.equals(value)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }
}
