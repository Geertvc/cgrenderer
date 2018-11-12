package abstractModel;

import java.io.Serializable;

/**
 * Implements a 3x3 matrix of floats. 
 */
public class Matrix3f implements Serializable, Cloneable {
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
	 * Constructs and initializes a Matrix3f to all zeros.	 
	 */
	public Matrix3f()
	{
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
	}

	/**
	 * Constructs and initializes a Matrix3f from the specified 9 element array
	 * @param m
	 */
	public Matrix3f(float m[])
	{
		m00 = m[0];
		m01 = m[1];
		m02 = m[2];
		m10 = m[3];
		m11 = m[4];
		m12 = m[5];
		m20 = m[6];
		m21 = m[7];
		m22 = m[8];
	}

	float[] get(){
		float[] m = new float[9];
		m[0] = m00;
		m[1] = m01;
		m[2] = m02;
		m[3] = m10;
		m[4] = m11;
		m[5] = m12;
		m[6] = m20;
		m[7] = m21;
		m[8] = m22;
		return m;
	}

	/**
	 * Constructs and initializes a Matrix3f from the specified 9 values
	 * @param m00
	 * @param m01
	 * @param m02
	 * @param m10
	 * @param m11
	 * @param m12
	 * @param m20
	 * @param m21
	 * @param m22
	 */	
	public Matrix3f(
			float m00, float m01, float m02, 
			float m10, float m11, float m12,
			float m20, float m21, float m22
			){
		this.m00 = m00;
		this.m01 = m01;
		this.m02 = m02;
		this.m10 = m10;
		this.m11 = m11;
		this.m12 = m12;
		this.m20 = m20;
		this.m21 = m21;
		this.m22 = m22;
	}


	/**
	 * Constructs a new matrix with the same values as the Matrix3f parameter.
	 * @param matrix3f
	 */
	public Matrix3f(Matrix3f matrix3f)
	{
		m00 = matrix3f.m00;
		m01 = matrix3f.m01;
		m02 = matrix3f.m02;
		m10 = matrix3f.m10;
		m11 = matrix3f.m11;
		m12 = matrix3f.m12;
		m20 = matrix3f.m20;
		m21 = matrix3f.m21;
		m22 = matrix3f.m22;
	}


	/**
	 * Returns a string that contains the values of this Matrix3f.
	 */

	public String toString()
	{
		return m00 + ", " + m01 + ", " + m02 + "\n" + m10 + ", " + m11 + ", " + m12 + "\n" + m20 + ", " + m21 + ", " + m22 + "\n";
	}


	/**
	 * Sets this Matrix3f to identity.
	 */
	public final void setIdentity()
	{
		m00 = 1.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m10 = 0.0F;
		m11 = 1.0F;
		m12 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 1.0F;
	}


	/**
	 * Sets the specified element of this matrix3f to the value provided.
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
			}
			break;
		}
		throw new ArrayIndexOutOfBoundsException();
	}


	/**
	 * Copies the matrix values in the specified row into the vector parameter.
	 * @param row
	 * @param vector3f
	 */

	public final void getRow(int row, Vector3f v)
	{
		if(row == 0)
		{
			v.x = m00;
			v.y = m01;
			v.z = m02;
		} else
			if(row == 1)
			{
				v.x = m10;
				v.y = m11;
				v.z = m12;
			} else
				if(row == 2)
				{
					v.x = m20;
					v.y = m21;
					v.z = m22;
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
		} else
			if(row == 1)
			{
				v[0] = m10;
				v[1] = m11;
				v[2] = m12;
			} else
				if(row == 2)
				{
					v[0] = m20;
					v[1] = m21;
					v[2] = m22;
				} else
				{
					throw new ArrayIndexOutOfBoundsException();
				}
	}


	/**
	 * Copies the matrix values in the specified column into the vector parameter.
	 * @param i
	 * @param vector3f
	 */

	public final void getColumn(int column, Vector3f v)
	{
		if(column == 0)
		{
			v.x = m00;
			v.y = m10;
			v.z = m20;
		} else
			if(column == 1)
			{
				v.x = m01;
				v.y = m11;
				v.z = m21;
			} else
				if(column == 2)
				{
					v.x = m02;
					v.y = m12;
					v.z = m22;
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
		} else
			if(column == 1)
			{
				v[0] = m01;
				v[1] = m11;
				v[2] = m21;
			} else
				if(column == 2)
				{
					v[0] = m02;
					v[1] = m12;
					v[2] = m22;
				} else
				{
					throw new ArrayIndexOutOfBoundsException();
				}
	}


	/**
	 * Sets the specified row of this matrix3f to the three values provided.
	 * @param row
	 * @param m0
	 * @param m1
	 * @param m2
	 */

	public final void setRow(int row, float m0, float m1, float m2)
	{
		switch(row)
		{
		case 0:
			m00 = m0;
			m01 = m1;
			m02 = m2;
			break;

		case 1:
			m10 = m0;
			m11 = m1;
			m12 = m2;
			break;

		case 2:
			m20 = m0;
			m21 = m1;
			m22 = m2;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified row of this matrix3f to the Vector provided.
	 * @param row
	 * @param v
	 */

	public final void setRow(int row, Vector3f v)
	{
		switch(row)
		{
		case 0:
			m00 = v.x;
			m01 = v.y;
			m02 = v.z;
			break;

		case 1:
			m10 = v.x;
			m11 = v.y;
			m12 = v.z;
			break;

		case 2:
			m20 = v.x;
			m21 = v.y;
			m22 = v.z;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified row of this matrix3f to the three values provided in the passed array.
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
			break;

		case 1:
			m10 = v[0];
			m11 = v[1];
			m12 = v[2];
			break;

		case 2:
			m20 = v[0];
			m21 = v[1];
			m22 = v[2];
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix3f to the three values provided.
	 * @param column
	 * @param m0
	 * @param m1
	 * @param m2
	 */

	public final void setColumn(int column, float m0, float m1, float m2)
	{
		switch(column)
		{
		case 0:
			m00 = m0;
			m10 = m1;
			m20 = m2;
			break;

		case 1:
			m01 = m0;
			m11 = m1;
			m21 = m2;
			break;

		case 2:
			m02 = m0;
			m12 = m1;
			m22 = m2;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix3f to the vector provided.
	 * @param column
	 * @param v
	 */

	public final void setColumn(int column, Vector3f v)
	{
		switch(column)
		{
		case 0:
			m00 = v.x;
			m10 = v.y;
			m20 = v.z;
			break;

		case 1:
			m01 = v.x;
			m11 = v.y;
			m21 = v.z;
			break;

		case 2:
			m02 = v.x;
			m12 = v.y;
			m22 = v.z;
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the specified column of this matrix3f to the three values provided.
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
			break;

		case 1:
			m01 = v[0];
			m11 = v[1];
			m21 = v[2];
			break;

		case 2:
			m02 = v[0];
			m12 = v[1];
			m22 = v[2];
			break;

		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * Sets the value of this matrix to a copy of the passed matrix m.
	 * @param m
	 */

	public final void set(Matrix3f m)
	{
		m00 = m.m00;
		m01 = m.m01;
		m02 = m.m02;
		m10 = m.m10;
		m11 = m.m11;
		m12 = m.m12;
		m20 = m.m20;
		m21 = m.m21;
		m22 = m.m22;
	}


	/**
	 * Sets this matrix to all zeros.
	 */

	public final void setZero()
	{
		m00 = 0.0F;
		m01 = 0.0F;
		m02 = 0.0F;
		m10 = 0.0F;
		m11 = 0.0F;
		m12 = 0.0F;
		m20 = 0.0F;
		m21 = 0.0F;
		m22 = 0.0F;
	}


	/**
	 * clone
	 */

	public Object clone()
	{
		Matrix3f matrix3f = null;
		try
		{
			matrix3f = (Matrix3f)super.clone();
		}
		catch(CloneNotSupportedException clonenotsupportedexception)
		{
			throw new InternalError();
		}
		return matrix3f;
	}



}
