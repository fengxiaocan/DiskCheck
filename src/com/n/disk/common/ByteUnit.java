package com.n.disk.common;


public enum ByteUnit {
    BIT {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toBit(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toBit((double) size);
        }

        @Override
        public long toBit(long size) {
            return size;
        }

        @Override
        public long toB(long size) {
            return size / C0;
        }

        @Override
        public long toKB(long size) {
            return size / (C0 * C1);
        }

        @Override
        public long toMB(long size) {
            return size / (C0 * C2);
        }

        @Override
        public long toGB(long size) {
            return size / (C0 * C3);
        }

        @Override
        public long toTB(long size) {
            return size / (C0 * C4);
        }

        @Override
        public long toPB(long size) {
            return size / (C0 * C5);
        }

        @Override
        public long toEB(long size) {
            return size / (C0 * C6);
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size;
        }

        @Override
        protected double toB(double size) {
            return size / C0;
        }

        @Override
        protected double toKB(double size) {
            return size / (C0 * C1);
        }

        @Override
        protected double toMB(double size) {
            return size / (C0 * C2);
        }

        @Override
        protected double toGB(double size) {
            return size / (C0 * C3);
        }

        @Override
        protected double toTB(double size) {
            return size / (C0 * C4);
        }

        @Override
        protected double toPB(double size) {
            return size / (C0 * C5);
        }

        @Override
        protected double toEB(double size) {
            return size / (C0 * C6);
        }
    },
    B {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0, MAX / C0);
        }

        @Override
        public long toB(long size) {
            return size;
        }

        @Override
        public long toKB(long size) {
            return size / C1;
        }

        @Override
        public long toMB(long size) {
            return size / C2;
        }

        @Override
        public long toGB(long size) {
            return size / C3;
        }

        @Override
        public long toTB(long size) {
            return size / C4;
        }

        @Override
        public long toPB(long size) {
            return size / C5;
        }

        @Override
        public long toEB(long size) {
            return size / C6;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0;
        }

        @Override
        protected double toB(double size) {
            return size;
        }

        @Override
        protected double toKB(double size) {
            return size / C1;
        }

        @Override
        protected double toMB(double size) {
            return size / C2;
        }

        @Override
        protected double toGB(double size) {
            return size / C3;
        }

        @Override
        protected double toTB(double size) {
            return size / C4;
        }

        @Override
        protected double toPB(double size) {
            return size / C5;
        }

        @Override
        protected double toEB(double size) {
            return size / C6;
        }
    },
    KB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toKB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toKB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C1, MAX / (C0 * C1));
        }

        @Override
        public long toB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toKB(long size) {
            return size;
        }

        @Override
        public long toMB(long size) {
            return size / C1;
        }

        @Override
        public long toGB(long size) {
            return size / C2;
        }

        @Override
        public long toTB(long size) {
            return size / C3;
        }

        @Override
        public long toPB(long size) {
            return size / C4;
        }

        @Override
        public long toEB(long size) {
            return size / C5;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C1;
        }

        @Override
        protected double toB(double size) {
            return size * C1;
        }

        @Override
        protected double toKB(double size) {
            return size;
        }

        @Override
        protected double toMB(double size) {
            return size / C1;
        }

        @Override
        protected double toGB(double size) {
            return size / C2;
        }

        @Override
        protected double toTB(double size) {
            return size / C3;
        }

        @Override
        protected double toPB(double size) {
            return size / C4;
        }

        @Override
        protected double toEB(double size) {
            return size / C5;
        }
    },
    MB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toMB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toMB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C2, MAX / (C0 * C2));
        }

        @Override
        public long toB(long size) {
            return x(size, C2, MAX / C2);
        }

        @Override
        public long toKB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toMB(long size) {
            return size;
        }

        @Override
        public long toGB(long size) {
            return size / C1;
        }

        @Override
        public long toTB(long size) {
            return size / C2;
        }

        @Override
        public long toPB(long size) {
            return size / C3;
        }

        @Override
        public long toEB(long size) {
            return size / C4;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C2;
        }

        @Override
        protected double toB(double size) {
            return size * C2;
        }

        @Override
        protected double toKB(double size) {
            return size * C1;
        }

        @Override
        protected double toMB(double size) {
            return size;
        }

        @Override
        protected double toGB(double size) {
            return size / C1;
        }

        @Override
        protected double toTB(double size) {
            return size / C2;
        }

        @Override
        protected double toPB(double size) {
            return size / C3;
        }

        @Override
        protected double toEB(double size) {
            return size / C4;
        }
    },
    GB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toGB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toGB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C3, MAX / (C0 * C3));
        }

        @Override
        public long toB(long size) {
            return x(size, C3, MAX / C3);
        }

        @Override
        public long toKB(long size) {
            return x(size, C2, MAX / C2);
        }

        @Override
        public long toMB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toGB(long size) {
            return size;
        }

        @Override
        public long toTB(long size) {
            return size / C1;
        }

        @Override
        public long toPB(long size) {
            return size / C2;
        }

        @Override
        public long toEB(long size) {
            return size / C3;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C3;
        }

        @Override
        protected double toB(double size) {
            return size * C3;
        }

        @Override
        protected double toKB(double size) {
            return size * C2;
        }

        @Override
        protected double toMB(double size) {
            return size * C1;
        }

        @Override
        protected double toGB(double size) {
            return size;
        }

        @Override
        protected double toTB(double size) {
            return size / C1;
        }

        @Override
        protected double toPB(double size) {
            return size / C2;
        }

        @Override
        protected double toEB(double size) {
            return size / C3;
        }
    },
    TB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toTB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toTB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C4, MAX / (C0 * C4));
        }

        @Override
        public long toB(long size) {
            return x(size, C4, MAX / C4);
        }

        @Override
        public long toKB(long size) {
            return x(size, C3, MAX / C3);
        }

        @Override
        public long toMB(long size) {
            return x(size, C2, MAX / C2);
        }

        @Override
        public long toGB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toTB(long size) {
            return size;
        }

        @Override
        public long toPB(long size) {
            return size / C1;
        }

        @Override
        public long toEB(long size) {
            return size / C2;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C4;
        }

        @Override
        protected double toB(double size) {
            return size * C4;
        }

        @Override
        protected double toKB(double size) {
            return size * C3;
        }

        @Override
        protected double toMB(double size) {
            return size * C2;
        }

        @Override
        protected double toGB(double size) {
            return size * C1;
        }

        @Override
        protected double toTB(double size) {
            return size;
        }

        @Override
        protected double toPB(double size) {
            return size / C1;
        }

        @Override
        protected double toEB(double size) {
            return size / C2;
        }
    },
    PB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toPB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toPB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C5, MAX / (C0 * C5));
        }

        @Override
        public long toB(long size) {
            return x(size, C5, MAX / C5);
        }

        @Override
        public long toKB(long size) {
            return x(size, C4, MAX / C4);
        }

        @Override
        public long toMB(long size) {
            return x(size, C3, MAX / C3);
        }

        @Override
        public long toGB(long size) {
            return x(size, C2, MAX / C2);
        }

        @Override
        public long toTB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toPB(long size) {
            return size;
        }

        @Override
        public long toEB(long size) {
            return size / C1;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C5;
        }

        @Override
        protected double toB(double size) {
            return size * C5;
        }

        @Override
        protected double toKB(double size) {
            return size * C4;
        }

        @Override
        protected double toMB(double size) {
            return size * C3;
        }

        @Override
        protected double toGB(double size) {
            return size * C2;
        }

        @Override
        protected double toTB(double size) {
            return size * C1;
        }

        @Override
        protected double toPB(double size) {
            return size;
        }

        @Override
        protected double toEB(double size) {
            return size / C1;
        }
    },
    EB {
        @Override
        public long convert(long size, ByteUnit unit) {
            return unit.toEB(size);
        }

        @Override
        public double convertDouble(long size, ByteUnit unit) {
            return unit.toEB((double) size);
        }

        @Override
        public long toBit(long size) {
            return x(size, C0 * C6, MAX / (C0 * C6));
        }

        @Override
        public long toB(long size) {
            return x(size, C6, MAX / C6);
        }

        @Override
        public long toKB(long size) {
            return x(size, C5, MAX / C5);
        }

        @Override
        public long toMB(long size) {
            return x(size, C4, MAX / C4);
        }

        @Override
        public long toGB(long size) {
            return x(size, C3, MAX / C3);
        }

        @Override
        public long toTB(long size) {
            return x(size, C2, MAX / C2);
        }

        @Override
        public long toPB(long size) {
            return x(size, C1, MAX / C1);
        }

        @Override
        public long toEB(long size) {
            return size;
        }

        /*以下是double类型的转换*/
        @Override
        protected double toBit(double size) {
            return size * C0 * C6;
        }

        @Override
        protected double toB(double size) {
            return size * C6;
        }

        @Override
        protected double toKB(double size) {
            return size * C5;
        }

        @Override
        protected double toMB(double size) {
            return size * C4;
        }

        @Override
        protected double toGB(double size) {
            return size * C3;
        }

        @Override
        protected double toTB(double size) {
            return size * C2;
        }

        @Override
        protected double toPB(double size) {
            return size * C1;
        }

        @Override
        protected double toEB(double size) {
            return size;
        }
    };

    static final long C0 = 8;
    static final long C1 = 1024;
    static final long C2 = C1 * C1;
    static final long C3 = C2 * C1;
    static final long C4 = C3 * C1;
    static final long C5 = C4 * C1;
    static final long C6 = C5 * C1;

    static final long MAX = Long.MAX_VALUE;
    //static final BigInteger ZB = BigInteger.valueOf(EB).multiply(BigInteger.valueOf(KB));
    //static final BigInteger YB = ZB.multiply(BigInteger.valueOf(KB));
    //static final BigInteger BB = YB.multiply(BigInteger.valueOf(KB));
    //static final BigInteger NB = BB.multiply(BigInteger.valueOf(KB));
    //static final BigInteger DB = NB.multiply(BigInteger.valueOf(KB));
    //static final BigInteger CB = DB.multiply(BigInteger.valueOf(KB));
    //static final BigInteger XB = CB.multiply(BigInteger.valueOf(KB));

    /**
     * Scale d by m, checking for overflow.
     * This has a short name to make above code more readable.
     */
    static long x(long d, long m, long over) {
        if (d > over) return Long.MAX_VALUE;
        if (d < -over) return Long.MIN_VALUE;
        return d * m;
    }

    /*以下是double类型的转换*/
    protected double toBit(double size) {
        throw new AbstractMethodError();
    }

    protected double toB(double size) {
        throw new AbstractMethodError();
    }

    protected double toKB(double size) {
        throw new AbstractMethodError();
    }

    protected double toMB(double size) {
        throw new AbstractMethodError();
    }

    protected double toGB(double size) {
        throw new AbstractMethodError();
    }

    protected double toTB(double size) {
        throw new AbstractMethodError();
    }

    protected double toPB(double size) {
        throw new AbstractMethodError();
    }

    protected double toEB(double size) {
        throw new AbstractMethodError();
    }

    public long convert(long size, ByteUnit unit) {
        throw new AbstractMethodError();
    }

    public double convertDouble(long size, ByteUnit unit) {
        throw new AbstractMethodError();
    }

    public String convert(long size, ByteUnit unit, @IntRange(from = 0, to = 9) int accuracy) {
        return String.format("%." + accuracy + "f", convertDouble(size, unit));
    }

    /*以下是long类型的转换*/
    public long toBit(long size) {
        throw new AbstractMethodError();
    }

    public long toB(long size) {
        throw new AbstractMethodError();
    }

    public long toKB(long size) {
        throw new AbstractMethodError();
    }

    public long toMB(long size) {
        throw new AbstractMethodError();
    }

    public long toGB(long size) {
        throw new AbstractMethodError();
    }

    public long toTB(long size) {
        throw new AbstractMethodError();
    }

    public long toPB(long size) {
        throw new AbstractMethodError();
    }

    public long toEB(long size) {
        throw new AbstractMethodError();
    }

    /*以下是double类型的转换*/


    public double toDoubleBit(long size) {
        return toBit((double) size);
    }

    public double toDoubleB(long size) {
        return toB((double) size);
    }

    public double toDoubleKB(long size) {
        return toKB((double) size);
    }

    public double toDoubleMB(long size) {
        return toMB((double) size);
    }

    public double toDoubleGB(long size) {
        return toGB((double) size);
    }

    public double toDoubleTB(long size) {
        return toTB((double) size);
    }

    public double toDoublePB(long size) {
        return toPB((double) size);
    }

    public double toDoubleEB(long size) {
        return toEB((double) size);
    }

    /*以下是String类型的转换*/

    public String toBit(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleBit(size), accuracy);
    }

    public String toB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleB(size), accuracy);
    }

    public String toKB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleKB(size), accuracy);
    }

    public String toMB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleMB(size), accuracy);
    }

    public String toGB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleGB(size), accuracy);
    }

    public String toTB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleTB(size), accuracy);
    }

    public String toPB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoublePB(size), accuracy);
    }

    public String toEB(long size, @IntRange(from = 0, to = 9) int accuracy) {
        return DataUtils.format(toDoubleEB(size), accuracy);
    }
}
