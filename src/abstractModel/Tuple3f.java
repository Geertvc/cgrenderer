package abstractModel;
/**
 * A generic 3-element tuple that is represented by
 * single precision-floating point x,y,z coordinates.
 * 
 *
 *
 */

import java.io.Serializable;


public abstract class Tuple3f implements Serializable, Cloneable
{
    /**
     * The x coordinate.
     */

    public float x;


    /**
     * The y coordinate.
     */

    public float y;


    /**
     * The z coordinate.
     */

    public float z;

    /**
     * Constructs and initializes a Tuple3f to (0,0,0).
     */

    public Tuple3f()
    {
        x = 0.0F;
        y = 0.0F;
        z = 0.0F;
    }


    /**
     * Constructs and initializes a Tuple3f from the specified xyz coordinates.
     * @param x
     * @param y
     * @param z
     */ 

    public Tuple3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Constructs and initializes a Tuple3f from the array of length 3. 
     * @param t
     */
    public Tuple3f(float t[])
    {
        x = t[0];
        y = t[1];
        z = t[2];
    }


    /**
     * Constructs and initializes a Tuple3f from the specified Tuple3d.
     * @param t
     */

    public Tuple3f(Tuple3f t)
    {
        x = t.x;
        y = t.y;
        z = t.z;
    }


    /**
     * Returns a string that contains the values of this Tuple3f 
     */

    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }


    /**
     *  Sets the value of this tuple to the specified xyz coordinates.
     * @param x
     * @param y
     * @param z
     */

    public final void set(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Sets the value of this tuple to the xyz coordinates specified in the array of length 3.
     * @param t
     */

    public final void set(float t[])
    {
        x = t[0];
        y = t[1];
        z = t[2];
    }


    /**
     * Sets the value of this tuple to the value of tuple t1.
     * @param t
     */

    public final void set(Tuple3f t)
    {
        x = t.x;
        y = t.y;
        z = t.z;
    }


    /**
     *   Gets the value of this tuple and copies the values into t.
     * @param t
     */

    public final void get(float t[])
    {
        t[0] = x;
        t[1] = y;
        t[2] = z;
    }


    /**
     * Gets the value of this tuple and copies the values into t.
     * @param t
     */

    public final void get(Tuple3f t)
    {
        t.x = x;
        t.y = y;
        t.z = z;
    }
    
    /**
     * Set the value of this tuple to the negated value of the Tuple3f argument.
     * Pay attention that a negation of 0.0f is -0.0f!
     * @param t
     */
    public final void negate(Tuple3f t){
    	x = -t.x;
    	y = -t.y;
    	z = -t.z;
    }
    
    /**
     * Adds the value of this tuple to the value of the Tuple3f argument.
     * @param t
     */
    public final void add(Tuple3f t){
    	x += t.x;
    	y += t.y;
    	z += t.z;
    }
    
    /**
     * Sets the value of this tuple to the value of two added Tuple3f arguments.
     * @param t
     */
    public final void addSet(Tuple3f t1, Tuple3f t2){
    	x = t1.x + t2.x;
    	y = t1.y + t2.y;
    	z = t1.z + t2.z;
    }
    
    /**
     * Sets the value of this tuple to the value of the first Tuple3f argument minus the second Tuple3f argument.
     * @param t
     */
    public final void substractSet(Tuple3f t1, Tuple3f t2){
    	x = t1.x - t2.x;
    	y = t1.y - t2.y;
    	z = t1.z - t2.z;
    }
    
    /**
     * Multiplies the values of this tuple with the argument.
     * @param t
     */
    public final void scale(float t){
    	x *= t;
    	y *= t;
    	z *= t;
    }
    
    /**
     * Sets the values of this tuple to the scaled tuple t.
     * @param t
     */
    public final void scaleSet(float s, Tuple3f t){
    	x = t.x*s;
    	y = t.y*s;
    	z = t.z*s;
    }
    
    /**
     * Sets the value of this tuple to the scaled tuple t1 and then adds tuple t2.
     * @param s
     * @param t1
     * @param t2
     */
    public final void scaleAdd(float s, Tuple3f t1, Tuple3f t2){
    	x = t1.x*s + t2.x;
    	y = t1.y*s + t2.y;
    	z = t1.z*s + t2.z;
    }
    
    /**
     * Sets the value of this tuple to the linear interpolated given tuples with the given coefficient.
     * Pseudocode: this = t1*f + t2*(1-f)
     *  
     * @param t1	The first Tuple3f to interpolate.
     * @param t2	The second Tuple3f to interpolate.
     * @param f	The intepolation coefficient.
     */
	public final void interpolate(Tuple3f t1, Tuple3f t2, float f) {
		x = (t1.x*f) + (t2.x*(1-f));
		y = (t1.y*f) + (t2.y*(1-f));
		z = (t1.z*f) + (t2.z*(1-f));
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple3f other = (Tuple3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}


	/**
     *  Creates a new object of the same class as this object.
     */	

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch(CloneNotSupportedException clonenotsupportedexception)
        {
            throw new InternalError();
        }
    }
}
