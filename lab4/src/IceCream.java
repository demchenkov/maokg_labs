import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IceCream implements ActionListener{
    private TransformGroup iceCreamGroup;
    private Transform3D trans = new Transform3D();
    private float angle = 1.0f;

    public static void main(String[] args) {
        new IceCream();
    }

    public IceCream() {
        Timer timer = new Timer(50, this);
        timer.start();
        BranchGroup scene = createSceneGraph();
        SimpleUniverse u = new SimpleUniverse();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        iceCreamGroup = new TransformGroup();
        iceCreamGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        buildIceCream();

        objRoot.addChild(iceCreamGroup);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, -0.8, 0.0),100.0);
        Color3f light1Color = new Color3f(0.5f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, 12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        // встановлюємо навколишнє освітлення
        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }

    private void buildIceCream() {
        float width = 0.3f, height = 0.6f;
        createHorn(width/2, height, 0, 0, 0, new Color3f(0.541f, 0.302f, 0.117f));

        float radius = (float) (width / (2 * ( 1 + Math.sin(Math.PI/3))));
        createBall(radius, radius, height / 2, .0f, new Color3f(1f, 0.631f, 0.8f));
        createBall(radius, -(width - 3 * radius), height / 2, radius, new Color3f(0.38f, 0.945f, 0.7059f));
        createBall(radius, -(width - 3 * radius), height / 2, -radius, new Color3f(1f, 0.886f, 0.412f));

        createBall( 1.2f * radius, 0, height / 2 + radius, 0, new Color3f(1f, 1f, 1f));

        createLeg(width/1.5f, 0.001f, 0, -height/2, 0, new Color3f(0.992f, 0.972f, 0.886f));
    }

    private void createBall(float radius, float x, float y, float z, Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Sphere cone = IceBall.getSphere(radius,  emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cone);
        iceCreamGroup.addChild(tg);
    }

    private void createHorn(float radius, float height, float x, float y, float z, Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Cone cone = IceBall.getHorn(radius, height, emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        transform.rotX(Math.PI);
        tg.setTransform(transform);
        tg.addChild(cone);
        iceCreamGroup.addChild(tg);
    }

    private void createLeg(float radius, float height, float x, float y, float z, Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Cylinder cylinder = IceBall.getLeg(radius, height, emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cylinder);
        iceCreamGroup.addChild(tg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        trans.rotY(angle);
        iceCreamGroup.setTransform(trans);
        angle += 0.05;
    }
}