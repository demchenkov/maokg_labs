import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

public class IceBall {
    public static Cylinder getLeg(float radius, float height, Color3f emmissiveColor) {
        Appearance ap = getXMassBallsAppearence(emmissiveColor);
        return new Cylinder(radius, height, ap);
    }

    public static Cone getHorn(float radius, float height, Color3f emmissiveColor) {
        Appearance ap = getXMassBallsAppearence(emmissiveColor);
        return new Cone(radius, height, ap);
    }
    public static Sphere getSphere(float radius, Color3f emissiveColor) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Sphere(radius, primflags, getXMassBallsAppearence(emissiveColor));
    }
    private static Appearance getXMassBallsAppearence(Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.2f, 0.15f, .15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }
}
