/*=========================================================================

  Program:   OpenIGTLink Library
  Module:    $HeadURL: http://osfe.org/OpenIGTLink/Source/org/medcare/igtl/messages/ImageMessage.java $
  Language:  java
  Date:      $Date: 2010-18-14 10:37:44 +0200 (ven., 13 nov. 2009) $
  Version:   $Revision: 0ab$

  Copyright (c) Absynt Technologies Ltd. All rights reserved.

  This software is distributed WITHOUT ANY WARRANTY; without even
  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE.  See the above copyright notices for more information.

=========================================================================*/

package org.medcare.igtl.messages;

import org.medcare.igtl.util.BytesArray;
import org.medcare.igtl.util.Header;

/**
 *** This class create an Image objet from bytes received or help to generate
 * bytes to send from it
 * 
 * @author Andre Charles Legendre
 * 
 */
public class ImageMessage extends OpenIGTMessage {

	public static int IGTL_IMAGE_HEADER_SIZE = 72;
	public static int IGTL_IMAGE_HEADER_VERSION = 1;

	public static int COORDINATE_LPS = 0;
	public static int COORDINATE_RAS = 1;

	public static int ENDIAN_BIG = 0;
	public static int ENDIAN_LITTLE = 1;

	public static int DTYPE_SCALAR = 1;
	public static int DTYPE_VECTOR = 2;

	public static final int TYPE_INT8 = 2;
	public static final int TYPE_UINT8 = 3;
	public static final int TYPE_INT16 = 4;
	public static final int TYPE_UINT16 = 5;
	public static final int TYPE_INT32 = 6;
	public static final int TYPE_UINT32 = 7;
	public static final int TYPE_FLOAT32 = 10;
	public static final int TYPE_FLOAT64 = 11;

	long version; // V unsigned int 16bits version
	long data_type = DTYPE_SCALAR; // T unsingned int 8bits image_type data_type
	// dataType
	long scalar_type = TYPE_UINT8; // S unsingned int 8bits scalar_type
	// scalarType
	long endian = ENDIAN_BIG; // E unsingned int 8bits endian_type endian endian
	long coordinate_type = COORDINATE_RAS; // O unsingned int 8bits
	// coordinate_type coord coordinate
	long dimensions[] = new long[3]; // unsingned int 16bits
	// RI_pixels unsingned int 16bits size[0] dimensions[0]
	// RJ_pixels unsingned int 16bits size[1] dimensions[1]
	// RK_pixels unsingned int 16bits size[2] dimensions[2]
	double matrix[][] = new double[4][4];
	double origin[] = new double[3]; // float 32bits
	// PX_pixels float 32bits origin[0] (matrix[0][3])
	// PY_pixels float 32bits origin[1] (matrix[1][3])
	// PZ_pixels float 32bits origin[2] (matrix[2][3])
	double normals[][] = new double[3][3];
	double norm_i[] = new double[3]; // float 32bits
	// TX_pixels float 32bits norm_i[0] (matrix[0][0])
	// TY_pixels float 32bits norm_i[1] (matrix[1][0])
	// TZ_pixels float 32bits norm_i[2] (matrix[2][0])
	double norm_j[] = new double[3]; // float 32bits
	// SX_pixels float 32bits norm_j[0] (matrix[0][1])
	// SY_pixels float 32bits norm_j[1] (matrix[1][1])
	// SZ_pixels float 32bits norm_j[2] (matrix[2][1])
	double norm_k[] = new double[3]; // float 32bits
	// NX_pixels // float 32bits norm_k[0] (matrix[0][2])
	// NY_pixels // float 32bits norm_k[1] (matrix[1][2])
	// NZ_pixels // float 32bits norm_k[2] (matrix[2][2])
	long subOffset[] = new long[3]; // unsingned int 16bits
	// DI_pixels unsingned int 16bits subvol_offset subOffset[0]
	// DJ_pixels unsingned int 16bits subvol_offset subOffset[1]
	// DK_pixels unsingned int 16bits subvol_offset subOffset[2]
	long subDimensions[] = new long[3]; // unsingned int 16bits
	// DRI_pixels unsingned int 16bits subvol_size subDimensions[0]
	// DRJ_pixels unsingned int 16bits subvol_size subDimensions[1]
	// DRK_pixels unsingned int 16bits subvol_size subDimensions[2]
	// spacing, igtl_image_get_matrix() calculates spacing based on the
	// transformation matrix
	double spacing[] = new double[3]; // float 32bits
	private int scalarType;
	private byte[] image_data;
	private BytesArray bytesArray;
	private byte[] image_header;

	/**
	 *** Constructor to be used to create message to send them with this
	 * constructor you must use method SetImageHeader, then CreateBody and then
	 * getBytes to send them
	 *** 
	 * @param deviceType
	 *            Device Type
	 * @param deviceName
	 *            Device Name
	 **/
	public ImageMessage(String deviceType, String deviceName) {
		super(deviceType, deviceName);
	}

	/**
	 *** Constructor to be used to create message from received data
	 * 
	 * @param header
	 * @param body
	 */
	public ImageMessage(Header header, byte body[]) {
		super(header, body);
		image_header = new byte[Header.LENGTH];
		System.arraycopy(body, 0, image_header, 0, Header.LENGTH);
		SetImageHeader(image_header);
		matrix[3][0] = 0.0;
		matrix[3][1] = 0.0;
		matrix[3][2] = 0.0;
		matrix[3][3] = 1.0;
	}

	/**
	 *** To create body from image_header and imageData
	 * 
	 * @param image_header byte arrary containing image header
	 * @param imageData byte arrary containing image data
	 */
	public byte[] CreateBody(byte image_header[], byte imageData[]) {
		body = new byte[Header.LENGTH + image_header.length + imageData.length];
		System.arraycopy(image_header, 0, body, 0, image_header.length);
		System.arraycopy(imageData, 0, body, image_header.length,
				imageData.length);
		header = new Header(VERSION_2, deviceType, deviceName, body);
		return body;
	}

	/**
	 * @param version
	 * @param data_type
	 * @param scalar_type
	 * @param endian
	 * @param coordinate_type
	 * @param dimensions
	 * @param origin
	 * @param normals
	 * @param subOffset
	 * @param subDimensions
	 */
	public byte[] SetImageHeader(long version, long data_type,
			long scalar_type, long endian, long coordinate_type,
			long dimensions[], double origin[], double normals[][],
			long subOffset[], long subDimensions[]) {
		bytesArray = new BytesArray();
		this.version = version;
		bytesArray.putULong(version, 2);
		this.data_type = data_type;
		bytesArray.putULong(data_type, 1);
		this.scalar_type = scalar_type;
		bytesArray.putULong(scalar_type, 1);
		this.endian = endian;
		bytesArray.putULong(endian, 1);
		this.coordinate_type = coordinate_type;
		bytesArray.putULong(coordinate_type, 1);
		this.dimensions = dimensions;
		bytesArray.putULong(dimensions[0], 2);
		bytesArray.putULong(dimensions[1], 2);
		bytesArray.putULong(dimensions[2], 2);
		SetOrigin(origin);
		bytesArray.putDouble(matrix[0][3], 4);
		bytesArray.putDouble(matrix[1][3], 4);
		bytesArray.putDouble(matrix[2][3], 4);
		SetNormals(normals);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				bytesArray.putDouble(matrix[i][j], 4);
		SetSubVolume(subDimensions, subOffset);
		matrix[3][0] = 0.0;
		matrix[3][1] = 0.0;
		matrix[3][2] = 0.0;
		matrix[3][3] = 1.0;
		bytesArray.putULong(subOffset[0], 2);
		bytesArray.putULong(subOffset[1], 2);
		bytesArray.putULong(subOffset[2], 2);
		bytesArray.putULong(subDimensions[0], 2);
		bytesArray.putULong(subDimensions[1], 2);
		bytesArray.putULong(subDimensions[2], 2);
		image_header = bytesArray.getBytes();
		return image_header;
	}

	/**
	 * @param dimensions
	 * @param origin
	 * @param normals
	 * @param subOffset
	 * @param subDimensions
	 */
	public byte[] SetImageHeader(long dimensions[], double origin[],
			double normals[][], long subOffset[], long subDimensions[]) {
		return SetImageHeader(this.version, this.data_type, this.scalar_type,
				this.endian, this.coordinate_type, dimensions, origin, normals,
				subOffset, subDimensions);
	}

	/**
	 * @param image_header
	 */
	public void SetImageHeader(byte image_header[]) {
		this.image_header = image_header;
		bytesArray = new BytesArray();
		bytesArray.putBytes(image_header);
		version = bytesArray.getLong(2); // unsigned Short
		data_type = bytesArray.getLong(1); // unsigned int8
		scalar_type = bytesArray.getLong(1); // unsigned int8
		endian = bytesArray.getLong(1); // unsigned int8
		coordinate_type = bytesArray.getLong(1); // unsigned int8
		dimensions[0] = bytesArray.getLong(2); // unsigned int16
		dimensions[1] = bytesArray.getLong(2); // unsigned int16
		dimensions[2] = bytesArray.getLong(2); // unsigned int16

		origin[0] = bytesArray.getDouble(4); // float32
		origin[1] = bytesArray.getDouble(4); // float32
		origin[2] = bytesArray.getDouble(4); // float32
		SetOrigin(origin);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				normals[i][j] = bytesArray.getDouble(4); // float32
		SetNormals(normals);
		subOffset[0] = bytesArray.getLong(2); // unsigned int16
		subOffset[1] = bytesArray.getLong(2); // unsigned int16
		subOffset[2] = bytesArray.getLong(2); // unsigned int16
		subDimensions[0] = bytesArray.getLong(2); // unsigned int16
		subDimensions[1] = bytesArray.getLong(2); // unsigned int16
		subDimensions[2] = bytesArray.getLong(2); // unsigned int16
		SetSubVolume(subDimensions, subOffset);
	}

	public byte[] GetImageHeader() {
		return image_header;
	}

	public byte[] GetImageData() {
		return image_data;
	}

	// Image dimensions
	public void SetDimensions(long s[]) {
		dimensions = s;
	}

	public void SetDimensions(long i, long j, long k) {
		dimensions[0] = i;
		dimensions[1] = j;
		dimensions[2] = k;
	}

	public long[] GetDimensions() {
		return dimensions;
	}

	public int SetSubVolume(long dim[], long off[]) {
		// make sure that sub-volume fits in the dimensions
		if (off[0] + dim[0] <= dimensions[0]
				&& off[1] + dim[1] <= dimensions[1]
				&& off[2] + dim[2] <= dimensions[2]) {
			subDimensions = dim;
			subOffset = off;
			return 1;
		} else {
			return 0;
		}
	}

	public int SetSubVolume(int dimi, int dimj, int dimk, int offi, int offj,
			int offk) {
		// make sure that sub-volume fits in the dimensions
		if (offi + dimi <= dimensions[0] && offj + dimj <= dimensions[1]
				&& offk + dimk <= dimensions[2]) {
			subDimensions[0] = dimi;
			subDimensions[1] = dimj;
			subDimensions[2] = dimk;
			subOffset[0] = offi;
			subOffset[1] = offj;
			subOffset[2] = offk;
			return 1;
		} else {
			return 0;
		}
	}

	public long[] GetSubDimensions() {
		return subDimensions;
	}

	public long[] GetSubOffset() {
		return subOffset;
	}

	public void SetSpacing(double s[]) {
		spacing = s;
	}

	public void SetSpacing(float si, float sj, float sk) {
		spacing[0] = si;
		spacing[1] = sj;
		spacing[2] = sk;
	}

	public double[] GetSpacing() {
		return spacing;
	}

	public void SetOrigin(double p[]) {
		matrix[0][3] = p[0];
		matrix[1][3] = p[1];
		matrix[2][3] = p[2];

		matrix[3][0] = 0.0;
		matrix[3][1] = 0.0;
		matrix[3][2] = 0.0;
		matrix[3][3] = 1.0;
	}

	public void SetOrigin(double px, double py, double pz) {
		matrix[0][3] = px;
		matrix[1][3] = py;
		matrix[2][3] = pz;

		matrix[3][0] = 0.0;
		matrix[3][1] = 0.0;
		matrix[3][2] = 0.0;
		matrix[3][3] = 1.0;
	}

	public double[] GetOrigin() {
		origin[0] = matrix[0][3];
		origin[1] = matrix[1][3];
		origin[2] = matrix[2][3];
		return origin;
	}

	void SetNormals(double o[][]) {
		matrix[0][0] = o[0][0];
		matrix[0][1] = o[0][1];
		matrix[0][2] = o[0][2];
		matrix[1][0] = o[1][0];
		matrix[1][1] = o[1][1];
		matrix[1][2] = o[1][2];
		matrix[2][0] = o[2][0];
		matrix[2][1] = o[2][1];
		matrix[2][2] = o[2][2];
	}

	void SetNormals(double t[], double s[], double n[]) {
		matrix[0][0] = t[0];
		matrix[1][0] = t[1];
		matrix[2][0] = t[2];
		matrix[0][1] = s[0];
		matrix[1][1] = s[1];
		matrix[2][1] = s[2];
		matrix[0][2] = n[0];
		matrix[1][2] = n[1];
		matrix[2][2] = n[2];
	}

	public double[][] GetNormals() {
		normals[0][0] = matrix[0][0];
		normals[0][1] = matrix[0][1];
		normals[0][2] = matrix[0][2];
		normals[1][0] = matrix[1][0];
		normals[1][1] = matrix[1][1];
		normals[1][2] = matrix[1][2];
		normals[2][0] = matrix[2][0];
		normals[2][1] = matrix[2][1];
		normals[2][2] = matrix[2][2];
		return normals;
	}

	public void SetMatrix(double matrix[][]) {
		this.matrix = matrix;
	}

	public double[][] GetMatrix() {
		return matrix;
	}

	// Image scalar type
	public void SetScalarType(int t) {
		scalarType = t;
	}

	public void SetScalarTypeToInt8() {
		scalarType = TYPE_INT8;
	}

	public void SetScalarTypeToUint8() {
		scalarType = TYPE_UINT8;
	}

	public void SetScalarTypeToInt16() {
		scalarType = TYPE_INT16;
	}

	public void SetScalarTypeToUint16() {
		scalarType = TYPE_UINT16;
	}

	public void SetScalarTypeToInt32() {
		scalarType = TYPE_INT32;
	}

	public void SetScalarTypeToUint32() {
		scalarType = TYPE_UINT32;
	}

	public int GetScalarType() {
		return scalarType;
	}

	// Endian of image scalar (default is ENDIAN_BIG)
	public void SetEndian(long e) {
		endian = e;
	}

	public long GetEndian() {
		return endian;
	}

	public long GetImageSize() {
		return dimensions[0] * dimensions[1] * dimensions[2] * GetScalarSize();
	}

	public long GetSubVolumeImageSize() {
		return subDimensions[0] * subDimensions[1] * subDimensions[2]
				* GetScalarSize();
	}

	private long GetScalarSize() {
		switch (scalarType) {
		case TYPE_INT8:
			return 1;
		case TYPE_UINT8:
			return 1;
		case TYPE_INT16:
			return 2;
		case TYPE_UINT16:
			return 2;
		case TYPE_INT32:
			return 4;
		case TYPE_UINT32:
			return 4;
		case TYPE_FLOAT32:
			return 4;
		case TYPE_FLOAT64:
			return 8;
		}
		return 0;
	}
}
