package abstractModel;


public class Point4f extends Tuple4f {
	
	 /**
     * Constructs and initializes a Point3f to (0,0,0).
     */

    public Point4f()
    {
    }


    /**
     * Constructs and initializes a Point3f from the specified xyz coordinates.
     * @param x
     * @param y
     * @param z
     */
    public Point4f(float x, float y, float z, float w)
    {
        super(x, y, z, w);
    }


    /**
     * Constructs and initializes a Point3f from the specified Point3f.
     * @param p
     */

    public Point4f(Point4f p)
    {
        super(p);
    }


    /**
     * Constructs and initializes a Point3f from the specified Tuple3f.
     * @param t
     */

    public Point4f(Tuple4f t)
    {
        super(t);
    }
    

    /**
     * Constructs and initializes a Point3f from the specified Tuple3f.
     * @param t
     */

    public Point4f(Tuple3f t)
    {
        super(t.x, t.y, t.z, 1F);
    }


    /**
     * Constructs and initializes a Point3f from the array of length 4.
     * @param p
     */

    public Point4f(float p[])
    {
        super(p);
    }

    /**
     * Converts this Point4f to a Point3f, using the method:
     * (x, y ,z ,w) = (x/w, y/w, z/w)
     * @return	Point3f
     * 		The converted Point4f.
     * @throws	IllegalArgumentException
     * 		If the w value of this Point4f is 0.0F
     */
    public final Point3f convertTo3f() throws IllegalArgumentException{
    	if(this.w == 0.0F)
    		throw new IllegalArgumentException("Tried to convert a point4f with w=0 to point3f");
    	return new Point3f(this.x/this.w, this.y/this.w, this.z/this.w);
    }

}
