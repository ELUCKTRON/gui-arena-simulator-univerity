package dev.saeedkhanloo.config;

public class SimulationConfig {
    public class Dragon {
        public class Red {
            public static final int HP = 120;
            public static final int DMG = 50;
            public static final int DMG_THRESHOLD = 20;
        }

        public class Black {
            public static final int HP = 200;
            public static final int DMG = 40;
            public static final int DMG_THRESHOLD = 35;
        }
    }

    public class Orc {
        public class Fighter {
            public static final int HP = 100;
            public static final int DMG = 25;
        }

        public class Defender {
            public static final int HP = 120;
            public static final int DMG = 15;
        }

        public class Berserker {
            public static final int HP = 80;
            public static final int DMG = 45;
        }
    }

    public class MainCharacter {
        public static final int HP = 130;
        public static final int DMG = 50;
        public static final double Armor = 1.5;
    }
}
