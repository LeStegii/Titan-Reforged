package net.kettlemc.titan.content.block;

/**
 * Wrapper class for creating blocks with certain settings
 */
public class ToolEnums {

    public enum TitanToolType {
        PICKAXE("pickaxe"),
        AXE("axe"),
        SHOVEL("shovel");

        private String id;

        TitanToolType(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public static TitanToolType getById(String id) {
            for (TitanToolType type : values())
                if (type.getId().equals(id))
                    return type;
            return null;
        }
    }

    public enum TitanHarvestLevel {
        WOOD_GOLD(0),
        STONE(1),
        IRON(2),
        DIAMOND(3),
        TITAN(4);

        private int level;

        TitanHarvestLevel(int level) {
            this.level = level;
        }

        public int getLevel() {
            return this.level;
        }

        public static TitanHarvestLevel getByLevel(int level) {
            for (TitanHarvestLevel type : values())
                if (type.getLevel() == level)
                    return type;
            return null;
        }
    }
}
