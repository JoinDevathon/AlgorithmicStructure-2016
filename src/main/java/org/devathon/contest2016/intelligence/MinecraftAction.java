package org.devathon.contest2016.intelligence;

public enum MinecraftAction implements IAction {
    START(0),
    GO_NORTH(1),
    GO_EAST(2),
    GO_SOUTH(3),
    GO_WEST(4),
    DO_STUFF(5),
    EXIT(6);

    public final int value;

    private MinecraftAction(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public static MinecraftAction toAction(int i) {
    	MinecraftAction[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
        	MinecraftAction a = var1[var3];
            if(a.value == i) {
                return a;
            }
        }

        return null;
    }
}

interface IAction{
	int getValue();
}

