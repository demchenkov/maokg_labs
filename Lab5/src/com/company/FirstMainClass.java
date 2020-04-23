package com.company;

import com.microcrowd.loader.java3d.max3ds.Loader3DS; 

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

import java.awt.Color;
import javax.media.j3d.*;
import javax.media.j3d.Material;
import javax.vecmath.*;
import javax.media.j3d.Background;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;

public class FirstMainClass extends JFrame {
    static SimpleUniverse universe;
    static Scene scene;
    static Map<String, Shape3D> nameMap;
    static BranchGroup root;
    static Canvas3D canvas;
    
    static TransformGroup wholeHorse;
    static Transform3D transform3D;
    
    public FirstMainClass () throws IOException{
        configureWindow();
        configureCanvas();
        configureUniverse();
        addModelToUniverse();
        setHorseElementsList();
        addAppearance();
        addImageBackground();
        addLightToUniverse();
        addOtherLight();
        ChangeViewAngle();
        root.compile();
        universe.addBranchGraph(root);
    }
    
    private void configureWindow()  {
        setTitle("Horse Animation");
        setSize(760,640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void configureCanvas(){
        canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas,BorderLayout.CENTER);
    }
    
    private void configureUniverse(){
        root= new BranchGroup();
        universe= new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }
    
    private void addModelToUniverse() throws IOException{
        scene = getSceneFromFile("source_folder\\horse.obj");
     // scene=getSceneFromLwoFile("d://3dModels//Aspen.lwo");
        root=scene.getSceneGroup();
    }
    
    private void addLightToUniverse(){
        Bounds bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f color = new Color3f(65/255f, 30/255f, 25/255f);
        Vector3f lightdirection = new Vector3f(-1f,-1f,-1f);
        DirectionalLight dirlight = new DirectionalLight(color,lightdirection);
        dirlight.setInfluencingBounds(bounds);
        root.addChild(dirlight);
    }
    
    private void printModelElementsList(Map<String,Shape3D> nameMap){
        for (String name : nameMap.keySet()) {
            System.out.printf("Name: %s\n", name);}
    }
    
    private void setHorseElementsList() {
        nameMap=scene.getNamedObjects();
        //Print elements of your model:
        printModelElementsList(nameMap);
        
        wholeHorse = new TransformGroup();
        transform3D = new Transform3D();
        transform3D.setScale(new Vector3d(0.5,0.5,0.5));
        wholeHorse.setTransform(transform3D);
        root.removeChild(nameMap.get("10026_horse_v01"));
        wholeHorse.addChild(nameMap.get("10026_horse_v01"));
        wholeHorse.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(wholeHorse);
    }
    
    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader(path,"RGP", new Container());
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor( new Color4f( Color.BLUE ) );
        return texture;
    }             
    
    Material getMaterial() {
        Material material = new Material();
        material.setAmbientColor (new Color3f(Color.WHITE));
        material.setDiffuseColor ( new Color3f(Color.CYAN) );
        material.setSpecularColor( new Color3f(Color.WHITE));
        material.setShininess( 0.5f );
        material.setLightingEnable(true);
        return material;
    }
    
    private void addAppearance(){
        Appearance HorseAppearance = new Appearance();
        HorseAppearance.setTexture(getTexture("source_folder\\fur1.jpg"));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.COMBINE);
        HorseAppearance.setTextureAttributes(texAttr);
        HorseAppearance.setMaterial(getMaterial());
        Shape3D Horse = nameMap.get("10026_horse_v01");
        Horse.setAppearance(HorseAppearance);
    }
    
    private void addColorBackground(){
        Background background = new Background(new Color3f(Color.CYAN));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        background.setApplicationBounds(bounds);      
        root.addChild(background); 
    }
    
    private void addImageBackground(){
        TextureLoader t = new TextureLoader("source_folder\\background.jpg", canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }
    
    private void ChangeViewAngle(){
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        Vector3f translationVector = new Vector3f(0.0F, -1.2F, 6F);
        vpTranslation.setTranslation(translationVector);
        vpGroup.setTransform(vpTranslation);
    }
    
    private void addOtherLight(){
        Color3f directionalLightColor = new Color3f(Color.BLACK);
        Color3f ambientLightColor = new Color3f(Color.WHITE);
        Vector3f lightDirection = new Vector3f(-1F, -1F, -1F);

        AmbientLight ambientLight = new AmbientLight(ambientLightColor);
        DirectionalLight directionalLight = new DirectionalLight(directionalLightColor, lightDirection);

        Bounds influenceRegion = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);

        ambientLight.setInfluencingBounds(influenceRegion);
        directionalLight.setInfluencingBounds(influenceRegion); 
        root.addChild(ambientLight);
        root.addChild(directionalLight);
    }
   
    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags (ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(location));
    }
    
    //Not always works
    public static Scene getSceneFromLwoFile(String location) throws IOException {
        Lw3dLoader loader = new Lw3dLoader();
        return loader.load(new FileReader(location));
     }
    
    public static void main(String[]args){
        try {
            FirstMainClass window = new FirstMainClass();
            AnimationHorse HorseMovement = new AnimationHorse(wholeHorse, transform3D, window);
            window.addKeyListener(HorseMovement);
            window.setVisible(true);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
