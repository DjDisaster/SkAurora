
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

public class ExprVectorFromQuaternion extends SimpleExpression<Vector> {
    static {
        Skript.registerExpression(
                ExprVectorFromQuaternion.class, Vector.class, ExpressionType.SIMPLE,
                "rotation of %quaternion%"
        );
    }

    private Expression<Quaternionf> quaternion;

    @SuppressWarnings({"NullableProblems", "unchecked"})
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.quaternion = (Expression<Quaternionf>) exprs[0];
        return true;
    }



    public @Nullable Vector convert(Quaternionf quaternionf) {
        float w = quaternionf.w;
        float x = quaternionf.x;
        float y = quaternionf.y;
        float z = quaternionf.z;

        float sinr_cosp = 2 * (w * x + y * z);
        float cosr_cosp = 1 - 2 * (x * x + y * y);
        float xRotation = (float)Math.atan2(sinr_cosp, cosr_cosp);

        float sinp = 2 * (w * y - z * x);
        float yRotation;
        if (Math.abs(sinp) >= 1)
            yRotation = (float) Math.copySign(Math.PI / 2, sinp);
        else
            yRotation = (float) Math.asin(sinp);

        float siny_cosp = 2 * (w * z + x * y);
        float cosy_cosp = 1 - 2 * (y * y + z * z);
        float zRotation = (float)Math.atan2(siny_cosp, cosy_cosp);

        return new Vector(xRotation, yRotation, zRotation);
    }

    @Override
    public boolean isSingle() {
        return true;
    }



    @Override
    public @NotNull Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected @Nullable Vector[] get(Event event) {
        return new Vector[]{
                convert(quaternion.getSingle(event))
        };
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "idk what this is for";
    }
}