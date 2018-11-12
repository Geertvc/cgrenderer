/*
 * The following methods are based on the implementation found at "http://code.google.com/p/jmonkeyengine/source/browse/branches/jme3/src/core/com/jme3/math/Matrix4f.java?r=6886":
 * - invert(Matrix4f)
 * - multiplyRight(Matrix4f)
 * - multiplyRight(Matrix4f, Matrix4f)
 * - transpose()
 * 
 * These methods thus need the following copyright notice:
 * 
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package abstractModel;

import java.io.Serializable;

/**
 * Implements a 4x4 matrix of floats. 
 */
public class Matrix4f implements Serializable, Cloneable {
	private static final long serialVersionUID = -7179035142297708259L;


	/**
	 * The first element of the first row.
	 */
	public float m00;


	/**
	 * The second element of the first row.
	 */
	public float m01;


	/**
	 * The third element of the first row.
	 */
	public float m02;


	/**
	 * The forth element of the first row.
	 */
	public float m03;


	/**
	 * The first element of the second row.
	 */
	public float m10;


	/**
	 * The second element of the second row.
	 */
	public float m11;


	/**
	 * The third element of the second row.
	 */
	public float m12;


	/**
	 * The fourth element of the second row.
	 */
	public float m13;


	/**
	 * The first element of the third row.
	 */
	public float m20;


	/**
	 * The second element of the third row.
	 */
	public float m21;


	/**
	 * The third element of the third row.
	 */
	public float m22;


	/**
	 * The fourth element of the third row.
	 */
	public float m23;


	/**
	 * The first element of the fourth row.
	 */
	public float m30;


	/**
	 * The second element of the fourth row.
	 */
	public float m31;


	/**
	 * The third element of the fourth row.
	 */
	public float m32;


	/**
	 * The fourth element of the fourth row.
	 */
	public float m33;


	/**
	 * Constructs and initializes a Matrix4f to all zeros.	 
	 */
	public Matrix4f()
	{
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 0.0F;
	}

	/**
	 * Constructs and initializes a Matrix4f from the specified 16 element array
	 * @param m
	 */
	public Matrix4f(float m[])
	{
		m00 = m[0];
		m01 = m[1];
		m02 = m[2];
		m03 = m[3];
		m10 = m[4];
		m11 = m[5];
		m12 = m[6];
		m13 = m[7];
		m20 = m[8];
		m21 = m[9];
		m22 = m[10];
		m23 = m[11];
		m30 = m[12];
		m31 = m[13];
		m32 = m[14];
		m33 = m[15];
	}

	float[] get(){
		float[] m = new float[16];
		m[0] = m00;
		m[1] = m01;
		m[2] = m02;
		m[3] = m03;
		m[4] = m10;
		m[5] = m11;
		m[6] = m12;
		m[7] = m13;
		m[8] = m20;
		m[9] = m21;
		m[10] = m22;
		m[11] = m23;
		m[12] = m30;
		m[13] = m31;
		m[14] = m32;
		m[15] = m33;
		return m;
	}

	/**
	 * Constructs and initializes a Matrix4f from the specified 16 values
	 * @param m00
	 * @param m01
	 * @param m02
	 * @param m03
	 * @param m10
	 * @param m11
	 * @param m12
	 * @param m13
	 * @param m20
	 * @param m21
	 * @param m22
	 * @param m23
	 * @param m30
	 * @param m31
	 * @param m32
	 * @param m33
	 */	
	public Matrix4f(
			float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33
			){
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m03 = m03;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m30 = m30;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}


	/**
	 * Constructs a new matrix with the same values as the Matrix4f parameter.
	 * @param matrix4f
	 */
	public Matrix4f(Matrix4f matrix4f)
	{
		m00 = matrix4f.m00;
		m01 = matrix4f.m01;
		m02 = matrix4f.m02;
		m03 = matrix4f.m03;
		m10 = matrix4f.m10;
		m11 = matrix4f.m11;
		m12 = matrix4f.m12;
		m13 = matrix4f.m13;
		m20 = matrix4f.m20;
		m21 = matrix4f.m21;
		m22 = matrix4f.m22;
		m23 = matrix4f.m23;
		m30 = matrix4f.m30;
		m31 = matrix4f.m31;
		m32 = matrix4f.m32;
		m33 = matrix4f.m33;
	}
	
	/**
	 * Constructs a new matrix with the same values as the Matrix3f parameter, but with the column and row 0, 0, 0, 1 added
	 * @param matrix3f
	 */
	public Matrix4f(Matrix3f matrix3f)
	{
		m00 = matrix3f.m00;
		m01 = matrix3f.m01;
		m02 = matrix3f.m02;
		m03 = 0;
		m10 = matrix3f.m10;
		m11 = matrix3f.m11;
		m12 = matrix3f.m12;
		m13 = 0;
		m20 = matrix3f.m20;
		m21 = matrix3f.m21;
		m22 = matrix3f.m22;
		m23 = 0;
		m30 = 0;
		m31 = 0;
		m32 = 0;
		m33 = 1;
	}


	/**
	 * Returns a string that contains the values of this Matrix4f.
	 */
	public String toString()
	{
		return m00 + ", " + m01 + ", " + m02 + ", " + m03 + "\n" + m10 + ", " + m11 + ", " + m12 + ", " + m13 + "\n" + m20 + ", " + m21 + ", " + m22 + ", " + m23 + "\n" + m30 + ", " + m31 + ", " + m32 + ", " + m33 + "\n";
	}


	/**
	 * Sets this Matrix4f to identity.
	 */
	public final void setIdentity()
	{
		m00 = 1.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 1.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 1.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 1.0F;
	}


	/**
	 * Sets the specified element of this matrix4f to the value provided.
	 * @param row
	 * @param j
	 * @param f
	 */
	public final void setElement(int row, int column, float value)
	{
		switch(row){
		case 0:
			switch(column){
			case 0:
				m00 = value;
				break;

			case 1:
				m01 = value;
				break;

			case 2:
				m02 = value;
				break;

			case 3:
				m03 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 1:
			switch(column){
			case 0:
				m10 = value;
				break;

			case 1:
				m11 = value;
				break;

			case 2:
				m12 = value;
				break;

			case 3:
				m13 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 2:
			switch(column){
			case 0:
				m20 = value;
				break;

			case 1:
				m21 = value;
				break;

			case 2:
				m22 = value;
				break;

			case 3:
				m23 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		case 3:
			switch(column){
			case 0:
				m30 = value;
				break;

			case 1:
				m31 = value;
				break;

			case 2:
				m32 = value;
				break;

			case 3:
				m33 = value;
				break;

			default:
				throw new ArrayIndexOutOfBoundsException();
			}
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Retrieves the value at the specified row and column of this matrix.
	 * @param row
	 * @param column
	 * @return
	 */
	public final float getElement(int row, int column)
	{
		switch(row)	{
		default:
			break;
		case 0:
			switch(column)
			{
			case 0:
				return m00;

			case 1:
				return m01;

			case 2:
				return m02;

			case 3:
				return m03;
			}
			break;

		case 1:
			switch(column)
			{
			case 0:
				return m10;

			case 1:
				return m11;

			case 2:
				return m12;

			case 3:
				return m13;
			}
			break;

		case 2:
			switch(column)
			{
			case 0:
				return m20;

			case 1:
				return m21;

			case 2:
				return m22;

			case 3:
				return m23;
			}
			break;

		case 3:
			switch(column)
			{
			case 0:
				return m30;

			case 1:
				return m31;

			case 2:
				return m32;

			case 3:
				return m33;
			}
			break;
		}
		throw new ArrayIndexOutOfBoundsException();
	}


	/**
	 * Copies the matrix values in the specified row into the vector parameter.
	 * @param row
	 * @param vector4f
	 */
	public final void getRow(int row, Vector4f v)
	{
		if(row == 0)
		{
			v.x = m00;
			v.y = m01;
			v.z = m02;
			v.w = m03;
		} else
			if(row == 1)
			{
				v.x = m10;
				v.y = m11;
				v.z = m12;
				v.w = m13;
			} else
				if(row == 2)
				{
					v.x = m20;
					v.y = m21;
					v.z = m22;
					v.w = m23;
				} else
					if(row == 3)
					{
						v.x = m30;
						v.y = m31;
						v.z = m32;
						v.w = m33;
					} else
					{
						throw new ArrayIndexOutOfBoundsException();
					}
	}


	/**
	 * Copies the matrix values in the specified row into the array parameter.
	 * @param row
	 * @param af
	 */
	public final void getRow(int row, float v[])
	{
		if(row == 0)
		{
			v[0] = m00;
			v[1] = m01;
			v[2] = m02;
			v[3] = m03;
		} else
			if(row == 1)
			{
				v[0] = m10;
				v[1] = m11;
				v[2] = m12;
				v[3] = m13;
			} else
				if(row == 2)
				{
					v[0] = m20;
					v[1] = m21;
					v[2] = m22;
					v[3] = m23;
				} else
					if(row == 3)
					{
						v[0] = m30;
						v[1] = m31;
						v[2] = m32;
						v[3] = m33;
					} else
					{
						throw new ArrayIndexOutOfBoundsException();
					}
	}


	/**
	 * Copies the matrix values in the specified column into the vector parameter.
	 * @param i
	 * @param vector4f
	 */
	public final void getColumn(int column, Vector4f v)
	{
		if(column == 0)
		{
			v.x = m00;
			v.y = m10;
			v.z = m20;
			v.w = m30;
		} else
			if(column == 1)
			{
				v.x = m01;
				v.y = m11;
				v.z = m21;
				v.w = m31;
			} else
				if(column == 2)
				{
					v.x = m02;
					v.y = m12;
					v.z = m22;
					v.w = m32;
				} else
					if(column == 3)
					{
						v.x = m03;
						v.y = m13;
						v.z = m23;
						v.w = m33;
					} else
					{
						throw new ArrayIndexOutOfBoundsException();
					}
	}


	/**
	 * Copies the matrix values in the specified column into the array parameter.
	 * @param column
	 * @param v
	 */
	public final void getColumn(int column, float v[])
	{
		if(column == 0)
		{
			v[0] = m00;
			v[1] = m10;
			v[2] = m20;
			v[3] = m30;
		} else
			if(column == 1)
			{
				v[0] = m01;
				v[1] = m11;
				v[2] = m21;
				v[3] = m31;
			} else
				if(column == 2)
				{
					v[0] = m02;
					v[1] = m12;
					v[2] = m22;
					v[3] = m32;
				} else
					if(column == 3)
					{
						v[0] = m03;
						v[1] = m13;
						v[2] = m23;
						v[3] = m33;
					} else
					{
						throw new ArrayIndexOutOfBoundsException();
					}
	}


	/**
	 * Sets the specified row of this matrix4f to the four values provided.
	 * @param row
	 * @param m0
	 * @param m1
	 * @param m2
	 * @param m3
	 */
	public final void setRow(int row, float m0, float m1, float m2, float m3)
	{
		switch(row)
		{
		case 0:
			m00 = m0;
			m01 = m1;
			m02 = m2;
			m03 = m3;
			break;

		case 1:
			m10 = m0;
			m11 = m1;
			m12 = m2;
			m13 = m3;
			break;

		case 2:
			m20 = m0;
			m21 = m1;
			m22 = m2;
			m23 = m3;
			break;

		case 3:
			m30 = m0;
			m31 = m1;
			m32 = m2;
			m33 = m3;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified row of this matrix4f to the Vector provided.
	 * @param row
	 * @param v
	 */
	public final void setRow(int row, Vector4f v)
	{
		switch(row)
		{
		case 0:
			m00 = v.x;
			m01 = v.y;
			m02 = v.z;
			m03 = v.w;
			break;

		case 1:
			m10 = v.x;
			m11 = v.y;
			m12 = v.z;
			m13 = v.w;
			break;

		case 2:
			m20 = v.x;
			m21 = v.y;
			m22 = v.z;
			m23 = v.w;
			break;

		case 3:
			m30 = v.x;
			m31 = v.y;
			m32 = v.z;
			m33 = v.w;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified row of this matrix4f to the four values provided in the passed array.
	 * @param rowloat
	 */
	public final void setRow(int row, float [] v)
	{
		switch(row)
		{
		case 0:
			m00 = v[0];
			m01 = v[1];
			m02 = v[2];
			m03 = v[3];
			break;

		case 1:
			m10 = v[0];
			m11 = v[1];
			m12 = v[2];
			m13 = v[3];
			break;

		case 2:
			m20 = v[0];
			m21 = v[1];
			m22 = v[2];
			m23 = v[3];
			break;

		case 3:
			m30 = v[0];
			m31 = v[1];
			m32 = v[2];
			m33 = v[3];
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix4f to the four values provided.
	 * @param column
	 * @param m0
	 * @param m1
	 * @param m2
	 * @param m3
	 */
	public final void setColumn(int column, float m0, float m1, float m2, float m3)
	{
		switch(column)
		{
		case 0:
			m00 = m0;
			m10 = m1;
			m20 = m2;
			m30 = m3;
			break;

		case 1:
			m01 = m0;
			m11 = m1;
			m21 = m2;
			m31 = m3;
			break;

		case 2:
			m02 = m0;
			m12 = m1;
			m22 = m2;
			m32 = m3;
			break;

		case 3:
			m03 = m0;
			m13 = m1;
			m23 = m2;
			m33 = m3;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix4f to the vector provided.
	 * @param column
	 * @param v
	 */
	public final void setColumn(int column, Vector4f v)
	{
		switch(column)
		{
		case 0:
			m00 = v.x;
			m10 = v.y;
			m20 = v.z;
			m30 = v.w;
			break;

		case 1:
			m01 = v.x;
			m11 = v.y;
			m21 = v.z;
			m31 = v.w;
			break;

		case 2:
			m02 = v.x;
			m12 = v.y;
			m22 = v.z;
			m32 = v.w;
			break;

		case 3:
			m03 = v.x;
			m13 = v.y;
			m23 = v.z;
			m33 = v.w;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix4f to the four values provided.
	 * @param column
	 * @param v
	 */
	public final void setColumn(int column, float v[])
	{
		switch(column)
		{
		case 0:
			m00 = v[0];
			m10 = v[1];
			m20 = v[2];
			m30 = v[3];
			break;

		case 1:
			m01 = v[0];
			m11 = v[1];
			m21 = v[2];
			m31 = v[3];
			break;

		case 2:
			m02 = v[0];
			m12 = v[1];
			m22 = v[2];
			m32 = v[3];
			break;

		case 3:
			m03 = v[0];
			m13 = v[1];
			m23 = v[2];
			m33 = v[3];
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the value of this matrix to a copy of the passed matrix m.
	 * @param m
	 */
	public final void set(Matrix4f m)
	{
		m00 = m.m00;
		m01 = m.m01;
		m02 = m.m02;
		m03 = m.m03;
		m10 = m.m10;
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m20 = m.m20;
		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
		m30 = m.m30;
		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
	}


	/**
	 * Sets this matrix to all zeros.
	 */
	public final void setZero()
	{
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m03 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m13 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
		m23 = 0.0F;
		m30 = 0.0F;
		m31 = 0.0F;
		m32 = 0.0F;
		m33 = 0.0F;
	}


	/**
	 * clone
	 */
	public Object clone()
	{
		Matrix4f matrix4f = null;
		try
		{
			matrix4f = (Matrix4f)super.clone();
		}
		catch(CloneNotSupportedException clonenotsupportedexception)
		{
			throw new InternalError();
		}
		return matrix4f;
	}

	/**
	 * Return the multiplication of this matrix with the given matrix (is on the right hand side).
	 * 
	 * @param matrix	The matrix to multiply with to the right.
	 */
	public Matrix4f multiplyRight(Matrix4f matrix) {
		Matrix4f temp = new Matrix4f();
        temp.m00 = m00 * matrix.m00 + 
                m01 * matrix.m10 + 
                m02 * matrix.m20 + 
                m03 * matrix.m30;
        temp.m01 = m00 * matrix.m01 + 
                m01 * matrix.m11 + 
                m02 * matrix.m21 +
                m03 * matrix.m31;
        temp.m02 = m00 * matrix.m02 + 
                m01 * matrix.m12 + 
                m02 * matrix.m22 +
                m03 * matrix.m32;
        temp.m03 = m00 * matrix.m03 + 
                m01 * matrix.m13 + 
                m02 * matrix.m23 + 
                m03 * matrix.m33;
        
        temp.m10 = m10 * matrix.m00 + 
                m11 * matrix.m10 + 
                m12 * matrix.m20 +
                m13 * matrix.m30;
        temp.m11 = m10 * matrix.m01 +
                m11 * matrix.m11 +
                m12 * matrix.m21 +
                m13 * matrix.m31;
        temp.m12 = m10 * matrix.m02 +
                m11 * matrix.m12 + 
                m12 * matrix.m22 +
                m13 * matrix.m32;
        temp.m13 = m10 * matrix.m03 +
                m11 * matrix.m13 +
                m12 * matrix.m23 + 
                m13 * matrix.m33;

        temp.m20 = m20 * matrix.m00 + 
                m21 * matrix.m10 + 
                m22 * matrix.m20 +
                m23 * matrix.m30;
        temp.m21 = m20 * matrix.m01 + 
                m21 * matrix.m11 + 
                m22 * matrix.m21 +
                m23 * matrix.m31;
        temp.m22 = m20 * matrix.m02 + 
                m21 * matrix.m12 + 
                m22 * matrix.m22 +
                m23 * matrix.m32;
        temp.m23 = m20 * matrix.m03 + 
                m21 * matrix.m13 + 
                m22 * matrix.m23 +
                m23 * matrix.m33;

        temp.m30 = m30 * matrix.m00 + 
                m31 * matrix.m10 + 
                m32 * matrix.m20 +
                m33 * matrix.m30;
        temp.m31 = m30 * matrix.m01 + 
                m31 * matrix.m11 + 
                m32 * matrix.m21 +
                m33 * matrix.m31;
        temp.m32 = m30 * matrix.m02 + 
                m31 * matrix.m12 + 
                m32 * matrix.m22 +
                m33 * matrix.m32;
        temp.m33 = m30 * matrix.m03 + 
                m31 * matrix.m13 + 
                m32 * matrix.m23 +
                m33 * matrix.m33;
        return temp;
	}
	
	/**
	 * Set this matrix to the multiplication of matrix1 and matrix2.
	 * The second argument is on the right hand side.
	 * 
	 * @param matrix1	The first matrix of the multiplication (is on the left hand side).
	 * @param matrix2	The second matrix of the multiplication (is on the right hand side).
	 */
	public void multiplyRight(Matrix4f matrix1, Matrix4f matrix2) {
        m00 = matrix1.m00 * matrix2.m00 + 
        		matrix1.m01 * matrix2.m10 + 
                matrix1.m02 * matrix2.m20 + 
                matrix1.m03 * matrix2.m30;
        m01 = matrix1.m00 * matrix2.m01 + 
        		matrix1.m01 * matrix2.m11 + 
                matrix1.m02 * matrix2.m21 +
                matrix1.m03 * matrix2.m31;
        m02 = matrix1.m00 * matrix2.m02 + 
        		matrix1.m01 * matrix2.m12 + 
                matrix1.m02 * matrix2.m22 +
                matrix1.m03 * matrix2.m32;
        m03 = matrix1.m00 * matrix2.m03 + 
        		matrix1.m01 * matrix2.m13 + 
                matrix1.m02 * matrix2.m23 + 
                matrix1.m03 * matrix2.m33;
        
        m10 = matrix1.m10 * matrix2.m00 + 
        		matrix1.m11 * matrix2.m10 + 
                matrix1.m12 * matrix2.m20 +
                matrix1.m13 * matrix2.m30;
        m11 = matrix1.m10 * matrix2.m01 +
        		matrix1.m11 * matrix2.m11 +
                matrix1.m12 * matrix2.m21 +
                matrix1.m13 * matrix2.m31;
        m12 = matrix1.m10 * matrix2.m02 +
        		matrix1.m11 * matrix2.m12 + 
                matrix1.m12 * matrix2.m22 +
                matrix1.m13 * matrix2.m32;
        m13 = matrix1.m10 * matrix2.m03 +
        		matrix1.m11 * matrix2.m13 +
                matrix1.m12 * matrix2.m23 + 
                matrix1.m13 * matrix2.m33;

        m20 = matrix1.m20 * matrix2.m00 + 
        		matrix1.m21 * matrix2.m10 + 
                matrix1.m22 * matrix2.m20 +
                matrix1.m23 * matrix2.m30;
        m21 = matrix1.m20 * matrix2.m01 + 
        		matrix1.m21 * matrix2.m11 + 
                matrix1.m22 * matrix2.m21 +
                matrix1.m23 * matrix2.m31;
        m22 = matrix1.m20 * matrix2.m02 + 
        		matrix1.m21 * matrix2.m12 + 
                matrix1.m22 * matrix2.m22 +
                matrix1.m23 * matrix2.m32;
        m23 = matrix1.m20 * matrix2.m03 + 
        		matrix1.m21 * matrix2.m13 + 
                matrix1.m22 * matrix2.m23 +
                matrix1.m23 * matrix2.m33;

        m30 = matrix1.m30 * matrix2.m00 + 
        		matrix1.m31 * matrix2.m10 + 
                matrix1.m32 * matrix2.m20 +
                matrix1.m33 * matrix2.m30;
        m31 = matrix1.m30 * matrix2.m01 + 
        		matrix1.m31 * matrix2.m11 + 
                matrix1.m32 * matrix2.m21 +
                matrix1.m33 * matrix2.m31;
        m32 = matrix1.m30 * matrix2.m02 + 
        		matrix1.m31 * matrix2.m12 + 
                matrix1.m32 * matrix2.m22 +
                matrix1.m33 * matrix2.m32;
        m33 = matrix1.m30 * matrix2.m03 + 
        		matrix1.m31 * matrix2.m13 + 
                matrix1.m32 * matrix2.m23 +
                matrix1.m33 * matrix2.m33;
	}
	
	/**
	 * Scales all the entries of this matrix with the given factor.
	 * 
	 * @param factor	The factor to multiply the matrix by.
	 */
	public void scale(float factor) {
	       m00 *= factor;
	       m01 *= factor;
	       m02 *= factor;
	       m03 *= factor;
	       m10 *= factor;
	       m11 *= factor;
	       m12 *= factor;
	       m13 *= factor;
	       m20 *= factor;
	       m21 *= factor;
	       m22 *= factor;
	       m23 *= factor;
	       m30 *= factor;
	       m31 *= factor;
	       m32 *= factor;
	       m33 *= factor;
	}
	
	/**
	 * Returns a new Matrix that is a scaled version (with the given factor) of this matrix.
	 * 
	 * @param factor	The scale factor.
	 * @return	Matrix4f
	 * 		The scaled version of this matrix.
	 */
	public Matrix4f scaleReturn(float factor) {
		Matrix4f value = new Matrix4f();
		value.set(this);
		value.scale(factor);
		return value;
	}
	
	/**
	 * @return	The transpose of this matrix.
	 */
    public Matrix4f transpose() {
    	Matrix4f temp = new Matrix4f();
    	temp.m00 = m00;
    	temp.m01 = m10;
    	temp.m02 = m20;
    	temp.m03 = m30;
    	temp.m10 = m01;
    	temp.m11 = m11;
    	temp.m12 = m21;
    	temp.m13 = m31;
    	temp.m20 = m02;
    	temp.m21 = m12;
    	temp.m22 = m22;
    	temp.m23 = m32;
    	temp.m30 = m03;
    	temp.m31 = m13;
    	temp.m32 = m23;
    	temp.m33 = m33;
        return temp;
    }
    
    /**
     * Sets this matrix to the inverse of the given matrix.
     * 
     * @throws	SingularMatrixException
     * 		If the determinant of the given matrix is 0.
     */
    public void invert(Matrix4f matrix) throws SingularMatrixException{
        float fA0 = matrix.m00*matrix.m11 - matrix.m01*matrix.m10;
        float fA1 = matrix.m00*matrix.m12 - matrix.m02*matrix.m10;
        float fA2 = matrix.m00*matrix.m13 - matrix.m03*matrix.m10;
        float fA3 = matrix.m01*matrix.m12 - matrix.m02*matrix.m11;
        float fA4 = matrix.m01*matrix.m13 - matrix.m03*matrix.m11;
        float fA5 = matrix.m02*matrix.m13 - matrix.m03*matrix.m12;
        float fB0 = matrix.m20*matrix.m31 - matrix.m21*matrix.m30;
        float fB1 = matrix.m20*matrix.m32 - matrix.m22*matrix.m30;
        float fB2 = matrix.m20*matrix.m33 - matrix.m23*matrix.m30;
        float fB3 = matrix.m21*matrix.m32 - matrix.m22*matrix.m31;
        float fB4 = matrix.m21*matrix.m33 - matrix.m23*matrix.m31;
        float fB5 = matrix.m22*matrix.m33 - matrix.m23*matrix.m32;
        float fDet = fA0*fB5-fA1*fB4+fA2*fB3+fA3*fB2-fA4*fB1+fA5*fB0;
        if ( Math.abs(fDet) == 0f )
            throw new SingularMatrixException("This matrix cannot be inverted");
        m00 = + matrix.m11*fB5 - matrix.m12*fB4 + matrix.m13*fB3;
        m01 = - matrix.m01*fB5 + matrix.m02*fB4 - matrix.m03*fB3;
        m02 = + matrix.m31*fA5 - matrix.m32*fA4 + matrix.m33*fA3;
        m03 = - matrix.m21*fA5 + matrix.m22*fA4 - matrix.m23*fA3;
        m10 = - matrix.m10*fB5 + matrix.m12*fB2 - matrix.m13*fB1;
        m11 = + matrix.m00*fB5 - matrix.m02*fB2 + matrix.m03*fB1;
        m12 = - matrix.m30*fA5 + matrix.m32*fA2 - matrix.m33*fA1;
        m13 = + matrix.m20*fA5 - matrix.m22*fA2 + matrix.m23*fA1;
        m20 = + matrix.m10*fB4 - matrix.m11*fB2 + matrix.m13*fB0;
        m21 = - matrix.m00*fB4 + matrix.m01*fB2 - matrix.m03*fB0;
        m22 = + matrix.m30*fA4 - matrix.m31*fA2 + matrix.m33*fA0;
        m23 = - matrix.m20*fA4 + matrix.m21*fA2 - matrix.m23*fA0;
        m30 = - matrix.m10*fB3 + matrix.m11*fB1 - matrix.m12*fB0;
        m31 = + matrix.m00*fB3 - matrix.m01*fB1 + matrix.m02*fB0;
        m32 = - matrix.m30*fA3 + matrix.m31*fA1 - matrix.m32*fA0;
        m33 = + matrix.m20*fA3 - matrix.m21*fA1 + matrix.m22*fA0;  
        float fInvDet = 1.0f/fDet;
        scale(fInvDet);
    }
    
    /**
     * Multiplies this matrix with the given LOCATIONvector.
     * This means that translation is added (the fourth component of the vector is 1).
     * 
     * @param vect	The location vector.
     * @return	Tuple3f
     * 		This matrix multiplied with the given vector with translation.
     */
    public Tuple3f locationMult(Tuple3f vect) {
    	Tuple3f resultTuple = new Point3f();
        resultTuple.x = m00*vect.x + m01*vect.y + m02*vect.z + m03;
        resultTuple.y = m10*vect.x + m11*vect.y + m12*vect.z + m13;
        resultTuple.z = m20*vect.x + m21*vect.y + m22*vect.z + m23;
        return resultTuple;
    }

    /**
     * Multiplies this matrix with the given DIRECTIONvector.
     * This means that translation is left aside (the fourth component of the vector is 0).
     * 
     * @param vect	The direction vector.
     * @return	Vector3f
     * 		This matrix multiplied with the given vector without translation added.
     */
    public Tuple3f directionMult(Tuple3f vect) {
    	Tuple3f resultTuple = new Vector3f();
        resultTuple.x = m00*vect.x + m01*vect.y + m02*vect.z;
        resultTuple.y = m10*vect.x + m11*vect.y + m12*vect.z;
        resultTuple.z = m20*vect.x + m21*vect.y + m22*vect.z;
        return resultTuple;
    }
    
    public Point4f vectorMult(Tuple4f vect){
    	Point4f result = new Point4f();
    	result.x = m00*vect.x + m01*vect.y + m02*vect.z + m03*vect.w;
    	result.y = m10*vect.x + m11*vect.y + m12*vect.z + m13*vect.w;
    	result.z = m20*vect.x + m21*vect.y + m22*vect.z + m23*vect.w;
    	result.w = m30*vect.x + m31*vect.y + m32*vect.z + m33*vect.w;
    	return result;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(m00);
		result = prime * result + Float.floatToIntBits(m01);
		result = prime * result + Float.floatToIntBits(m02);
		result = prime * result + Float.floatToIntBits(m03);
		result = prime * result + Float.floatToIntBits(m10);
		result = prime * result + Float.floatToIntBits(m11);
		result = prime * result + Float.floatToIntBits(m12);
		result = prime * result + Float.floatToIntBits(m13);
		result = prime * result + Float.floatToIntBits(m20);
		result = prime * result + Float.floatToIntBits(m21);
		result = prime * result + Float.floatToIntBits(m22);
		result = prime * result + Float.floatToIntBits(m23);
		result = prime * result + Float.floatToIntBits(m30);
		result = prime * result + Float.floatToIntBits(m31);
		result = prime * result + Float.floatToIntBits(m32);
		result = prime * result + Float.floatToIntBits(m33);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrix4f other = (Matrix4f) obj;
		if (Float.floatToIntBits(m00) != Float.floatToIntBits(other.m00))
			return false;
		if (Float.floatToIntBits(m01) != Float.floatToIntBits(other.m01))
			return false;
		if (Float.floatToIntBits(m02) != Float.floatToIntBits(other.m02))
			return false;
		if (Float.floatToIntBits(m03) != Float.floatToIntBits(other.m03))
			return false;
		if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
			return false;
		if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
			return false;
		if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12))
			return false;
		if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13))
			return false;
		if (Float.floatToIntBits(m20) != Float.floatToIntBits(other.m20))
			return false;
		if (Float.floatToIntBits(m21) != Float.floatToIntBits(other.m21))
			return false;
		if (Float.floatToIntBits(m22) != Float.floatToIntBits(other.m22))
			return false;
		if (Float.floatToIntBits(m23) != Float.floatToIntBits(other.m23))
			return false;
		if (Float.floatToIntBits(m30) != Float.floatToIntBits(other.m30))
			return false;
		if (Float.floatToIntBits(m31) != Float.floatToIntBits(other.m31))
			return false;
		if (Float.floatToIntBits(m32) != Float.floatToIntBits(other.m32))
			return false;
		if (Float.floatToIntBits(m33) != Float.floatToIntBits(other.m33))
			return false;
		return true;
	}

}
