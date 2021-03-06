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

import java.io.IOException;
import java.math.BigInteger;

public final class TextFormat {
  private TextFormat() {}

  // =================================================================
  // Utility functions
  //
  // Some of these methods are package-private because Descriptors.java uses
  // them.

  /**
   * Escapes bytes in the format used in protocol buffer text format, which is the same as the
   * format used for C string literals. All bytes that are not printable 7-bit ASCII characters are
   * escaped, as well as backslash, single-quote, and double-quote characters. Characters for which
   * no defined short-hand escape sequence is defined will be escaped using 3-digit octal sequences.
   */
  public static String escapeBytes(ByteString input) {
    return TextFormatEscaper.escapeBytes(input);
  }

  /** Like {@link #escapeBytes(ByteString)}, but used for byte array. */
  public static String escapeBytes(byte[] input) {
    return TextFormatEscaper.escapeBytes(input);
  }

  /**
   * Un-escape a byte sequence as escaped using {@link #escapeBytes(ByteString)}. Two-digit hex
   * escapes (starting with "\x") are also recognized.
   */
  public static ByteString unescapeBytes(final CharSequence charString)
      throws InvalidEscapeSequenceException {
    // First convert the Java character sequence to UTF-8 bytes.
    ByteString input = ByteString.copyFromUtf8(charString.toString());
    // Then unescape certain byte sequences introduced by ASCII '\\'.  The valid
    // escapes can all be expressed with ASCII characters, so it is safe to
    // operate on bytes here.
    //
    // Unescaping the input byte array will result in a byte sequence that's no
    // longer than the input.  That's because each escape sequence is between
    // two and four bytes long and stands for a single byte.
    final byte[] result = new byte[input.size()];
    int pos = 0;
    for (int i = 0; i < input.size(); i++) {
      byte c = input.byteAt(i);
      if (c == '\\') {
        if (i + 1 < input.size()) {
          ++i;
          c = input.byteAt(i);
          if (isOctal(c)) {
            // Octal escape.
            int code = digitValue(c);
            if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
              ++i;
              code = code * 8 + digitValue(input.byteAt(i));
            }
            if (i + 1 < input.size() && isOctal(input.byteAt(i + 1))) {
              ++i;
              code = code * 8 + digitValue(input.byteAt(i));
            }
            // TODO: Check that 0 <= code && code <= 0xFF.
            result[pos++] = (byte) code;
          } else {
            switch (c) {
              case 'a':
                result[pos++] = 0x07;
                break;
              case 'b':
                result[pos++] = '\b';
                break;
              case 'f':
                result[pos++] = '\f';
                break;
              case 'n':
                result[pos++] = '\n';
                break;
              case 'r':
                result[pos++] = '\r';
                break;
              case 't':
                result[pos++] = '\t';
                break;
              case 'v':
                result[pos++] = 0x0b;
                break;
              case '\\':
                result[pos++] = '\\';
                break;
              case '\'':
                result[pos++] = '\'';
                break;
              case '"':
                result[pos++] = '\"';
                break;

              case 'x':
                // hex escape
                int code = 0;
                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                  ++i;
                  code = digitValue(input.byteAt(i));
                } else {
                  throw new InvalidEscapeSequenceException(
                      "Invalid escape sequence: '\\x' with no digits");
                }
                if (i + 1 < input.size() && isHex(input.byteAt(i + 1))) {
                  ++i;
                  code = code * 16 + digitValue(input.byteAt(i));
                }
                result[pos++] = (byte) code;
                break;

              default:
                throw new InvalidEscapeSequenceException(
                    "Invalid escape sequence: '\\" + (char) c + '\'');
            }
          }
        } else {
          throw new InvalidEscapeSequenceException(
              "Invalid escape sequence: '\\' at end of string.");
        }
      } else {
        result[pos++] = c;
      }
    }

    return result.length == pos
        ? ByteString.wrap(result) // This reference has not been out of our control.
        : ByteString.copyFrom(result, 0, pos);
  }

  /**
   * Thrown by {@link TextFormat#unescapeBytes} and {@link TextFormat#unescapeText} when an invalid
   * escape sequence is seen.
   */
  public static class InvalidEscapeSequenceException extends IOException {
    private static final long serialVersionUID = -8164033650142593304L;

    InvalidEscapeSequenceException(final String description) {
      super(description);
    }
  }

  /**
   * Like {@link #escapeBytes(ByteString)}, but escapes a text string. Non-ASCII characters are
   * first encoded as UTF-8, then each byte is escaped individually as a 3-digit octal escape. Yes,
   * it's weird.
   */
  static String escapeText(final String input) {
    return escapeBytes(ByteString.copyFromUtf8(input));
  }

  /** Escape double quotes and backslashes in a String for unicode output of a message. */
  public static String escapeDoubleQuotesAndBackslashes(final String input) {
    return TextFormatEscaper.escapeDoubleQuotesAndBackslashes(input);
  }

  /**
   * Un-escape a text string as escaped using {@link #escapeText(String)}. Two-digit hex escapes
   * (starting with "\x") are also recognized.
   */
  static String unescapeText(final String input) throws InvalidEscapeSequenceException {
    return unescapeBytes(input).toStringUtf8();
  }

  /** Is this an octal digit? */
  private static boolean isOctal(final byte c) {
    return '0' <= c && c <= '7';
  }

  /** Is this a hex digit? */
  private static boolean isHex(final byte c) {
    return ('0' <= c && c <= '9') || ('a' <= c && c <= 'f') || ('A' <= c && c <= 'F');
  }

  /**
   * Interpret a character as a digit (in any base up to 36) and return the numeric value. This is
   * like {@code Character.digit()} but we don't accept non-ASCII digits.
   */
  private static int digitValue(final byte c) {
    if ('0' <= c && c <= '9') {
      return c - '0';
    } else if ('a' <= c && c <= 'z') {
      return c - 'a' + 10;
    } else {
      return c - 'A' + 10;
    }
  }

  /**
   * Parse a 32-bit signed integer from the text. Unlike the Java standard {@code
   * Integer.parseInt()}, this function recognizes the prefixes "0x" and "0" to signify hexadecimal
   * and octal numbers, respectively.
   */
  static int parseInt32(final String text) throws NumberFormatException {
    return (int) parseInteger(text, true, false);
  }

  /**
   * Parse a 32-bit unsigned integer from the text. Unlike the Java standard {@code
   * Integer.parseInt()}, this function recognizes the prefixes "0x" and "0" to signify hexadecimal
   * and octal numbers, respectively. The result is coerced to a (signed) {@code int} when returned
   * since Java has no unsigned integer type.
   */
  static int parseUInt32(final String text) throws NumberFormatException {
    return (int) parseInteger(text, false, false);
  }

  /**
   * Parse a 64-bit signed integer from the text. Unlike the Java standard {@code
   * Integer.parseInt()}, this function recognizes the prefixes "0x" and "0" to signify hexadecimal
   * and octal numbers, respectively.
   */
  static long parseInt64(final String text) throws NumberFormatException {
    return parseInteger(text, true, true);
  }

  /**
   * Parse a 64-bit unsigned integer from the text. Unlike the Java standard {@code
   * Integer.parseInt()}, this function recognizes the prefixes "0x" and "0" to signify hexadecimal
   * and octal numbers, respectively. The result is coerced to a (signed) {@code long} when returned
   * since Java has no unsigned long type.
   */
  static long parseUInt64(final String text) throws NumberFormatException {
    return parseInteger(text, false, true);
  }

  private static long parseInteger(final String text, final boolean isSigned, final boolean isLong)
      throws NumberFormatException {
    int pos = 0;

    boolean negative = false;
    if (text.startsWith("-", pos)) {
      if (!isSigned) {
        throw new NumberFormatException("Number must be positive: " + text);
      }
      ++pos;
      negative = true;
    }

    int radix = 10;
    if (text.startsWith("0x", pos)) {
      pos += 2;
      radix = 16;
    } else if (text.startsWith("0", pos)) {
      radix = 8;
    }

    final String numberText = text.substring(pos);

    long result = 0;
    if (numberText.length() < 16) {
      // Can safely assume no overflow.
      result = Long.parseLong(numberText, radix);
      if (negative) {
        result = -result;
      }

      // Check bounds.
      // No need to check for 64-bit numbers since they'd have to be 16 chars
      // or longer to overflow.
      if (!isLong) {
        if (isSigned) {
          if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            throw new NumberFormatException(
                "Number out of range for 32-bit signed integer: " + text);
          }
        } else {
          if (result >= (1L << 32) || result < 0) {
            throw new NumberFormatException(
                "Number out of range for 32-bit unsigned integer: " + text);
          }
        }
      }
    } else {
      BigInteger bigValue = new BigInteger(numberText, radix);
      if (negative) {
        bigValue = bigValue.negate();
      }

      // Check bounds.
      if (!isLong) {
        if (isSigned) {
          if (bigValue.bitLength() > 31) {
            throw new NumberFormatException(
                "Number out of range for 32-bit signed integer: " + text);
          }
        } else {
          if (bigValue.bitLength() > 32) {
            throw new NumberFormatException(
                "Number out of range for 32-bit unsigned integer: " + text);
          }
        }
      } else {
        if (isSigned) {
          if (bigValue.bitLength() > 63) {
            throw new NumberFormatException(
                "Number out of range for 64-bit signed integer: " + text);
          }
        } else {
          if (bigValue.bitLength() > 64) {
            throw new NumberFormatException(
                "Number out of range for 64-bit unsigned integer: " + text);
          }
        }
      }

      result = bigValue.longValue();
    }

    return result;
  }
}
