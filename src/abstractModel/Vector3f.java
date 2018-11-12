/* The methods angle and cross are based on the implementation of Vector3f.java found on
 * http://vecmath.sourcearchive.com/documentation/1.5.2/Vector3f_8java-source.html
 * and thus need the following Copyright notice.
 * 
 * 
 * Copyright 1997-2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package abstractModel;

import java.io.Serializable;


public class Vector3f extends Tuple3f implements Serializable
{
	/**
	 * Constructs and initializes a Vector3f to (0,0,0).
	 */

	public Vector3f()
	{
	}


	/**
	 * Constructs and initializes a Vector3f from the specified xyz coordinates.
	 * @param x
	 * @param y
	 * @param z
	 */

	public Vector3f(float x, float y, float z)
	{
		super(x, y, z);
	}


	/**
	 * Constructs and initializes a Vector3f from the specified Vector3f
	 * @param v
	 */

	public Vector3f(Vector3f v)
	{
		super(v);
	}


	/**
	 * Constructs and initializes a Vector3f from the specified Tuple3f.
	 * @param t
	 */

	public Vector3f(Tuple3f t)
	{
		super(t);
	}


	/**
	 * Constructs and initializes a Vector3f from the array of length 3.
	 * @param v
	 */

	public Vector3f(float v[])
	{
		super(v);
	}

	/**
	 * Returns the length of this vector.
	 * @return the length of this vector
	 */
	public final float length(){
		return (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
	}
	
	/**
	 * Returns the square of the length of this vector.
	 * @return the square of the length of this vector
	 */
	public final float lengthSquared(){
		return (this.x*this.x + this.y*this.y + this.z*this.z);
	}


	/**
	 * Computes the dot product of this vector and vector v1.
	 * @param v1 the other vector
	 * @return the dot product of this vector and v1
	 */
	public final float dotProduct(Vector3f v1){
		return ((this.x*v1.x) + (this.y*v1.y) + (this.z*v1.z));
	}

	/**
	 * Sets this vector to be the vector cross product of vectors v1 and v2.
	 * @param v1 the first vector
	 * @param v2 the second vector
	 */
	public final void cross(Vector3f v1, Vector3f v2) {
		this.x = v1.y*v2.z - v1.z*v2.y;
		this.y = v2.x*v1.z - v2.z*v1.x;
		this.z = v1.x*v2.y - v1.y*v2.x;
	}

	/** 
	 * Returns the angle in radians between this vector and the vector
	 * parameter. The return value is in the range [0,PI].
	 * @param v    the other vector 
	 * @return   the angle in radians in the range [0,PI] 
	 */   
	public final float angle(Vector3f v) { 
		double dotProduct = this.dotProduct(v) / ( this.length()*v.length() );
		if( dotProduct < -1.0){
			dotProduct = -1.0;
		}
		if( dotProduct >  1.0){ 
			dotProduct =  1.0;
		}
		return((float) (Math.acos( dotProduct )));
	}
	
	public final void normalize(){
		this.scale(1/this.length());
	}
	
	/**
	 * Returns a normalized vector pointing from the first point to the second point.
	 * 
	 * @param to	The point the vector points to.
	 * @param from	The point the vector starts from.
	 * @return	Vector3f
	 * 		The normalized vector pointing from the "from" point to the "to" point.
	 */
	public static Vector3f getNormalizedVectorBetween(Point3f to, Point3f from) {
		Vector3f diffVector = new Vector3f();
		diffVector.substractSet(to, from);
		diffVector.normalize();
		return diffVector;
	}
}
