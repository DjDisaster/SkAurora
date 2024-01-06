package me.djdisaster.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import javax.annotation.Nullable;

public class ExprQuaternionFromVector extends SimpleExpression<Quaternionf> {
    static {
        Skript.registerExpression(
                ExprQuaternionFromVector.class, Quaternionf.class, ExpressionType.SIMPLE,
                "quaternion from %vector%"
        );
    }

    private Expression<Vector> vector;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        Bukkit.broadcastMessage("INIT");
        this.vector = (Expression<Vector>) exprs[0];
        return true;
    }

    public @Nullable Quaternionf convert(Vector vector) {
        Bukkit.broadcastMessage("CALLED");
        float x = (float) Math.toRadians(vector.getX() * 90);
        float y = (float) Math.toRadians(vector.getY() * 90);
        float z = (float) Math.toRadians(vector.getZ() * 90);

        float cy = (float) Math.cos(z * 0.5);
        float sy = (float) Math.sin(z * 0.5);
        float cp = (float) Math.cos(y * 0.5);
        float sp = (float) Math.sin(y * 0.5);
        float cr = (float) Math.cos(x * 0.5);
        float sr = (float) Math.sin(x * 0.5);

        Quaternionf quaternion = new Quaternionf();
        quaternion.w = cr * cp * cy + sr * sp * sy;
        quaternion.x = sr * cp * cy - cr * sp * sy;
        quaternion.y = cr * sp * cy + sr * cp * sy;
        quaternion.z = cr * cp * sy - sr * sp * cy;

        return quaternion;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Quaternionf> getReturnType() {
        return Quaternionf.class;
    }

    @Override
    protected @Nullable Quaternionf[] get(Event event) {
        return new Quaternionf[]{
                convert(vector.getSingle(event))
        };
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "quaternion from vector";
    }
}
