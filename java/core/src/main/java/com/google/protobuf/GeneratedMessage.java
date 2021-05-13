// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.WireFormat.FieldType;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * All generated protocol message classes extend this class.  This class
 * implements most of the Message and Builder interfaces using Java reflection.
 * Users can ignore this class and pretend that generated messages implement
 * the Message interface directly.
 *
 * @author kenton@google.com Kenton Varda
 */
public abstract class GeneratedMessage extends AbstractMessage
    implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Type used to represent generated extensions.  The protocol compiler
   * generates a static singleton instance of this class for each extension.
   *
   * <p>For example, imagine you have the {@code .proto} file:
   *
   * <pre>
   * option java_class = "MyProto";
   *
   * message Foo {
   *   extensions 1000 to max;
   * }
   *
   * extend Foo {
   *   optional int32 bar;
   * }
   * </pre>
   *
   * <p>Then, {@code MyProto.Foo.bar} has type
   * {@code GeneratedExtension<MyProto.Foo, Integer>}.
   *
   * <p>In general, users should ignore the details of this type, and simply use
   * these static singletons as parameters to the extension accessors defined
   * in {@link ExtendableMessage} and {@link ExtendableBuilder}.
   */
  public static class GeneratedExtension<
      ContainingType extends Message, Type> extends
      Extension<ContainingType, Type> {

	@Override
	public FieldDescriptor getDescriptor() { // TODO Auto-generated method stub
	return null; }

	@Override
	protected ExtensionType getExtensionType() { // TODO Auto-generated method stub
	return null; }

	@Override
	public int getNumber() { // TODO Auto-generated method stub
	return 0; }

	@Override
	public FieldType getLiteType() { // TODO Auto-generated method stub
	return null; }

	@Override
	public boolean isRepeated() { // TODO Auto-generated method stub
	return false; }

	@Override
	public Type getDefaultValue() { // TODO Auto-generated method stub
	return null; }

	@Override
	public MessageLite getMessageDefaultInstance() { // TODO Auto-generated method stub
	return null; }
  }

  
  // =================================================================

}
