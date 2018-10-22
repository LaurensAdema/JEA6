using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Models;

namespace ma.ade.Kwetter2.Admin.Extensions
{
    public static class ByteExtensions
    {
        public static byte[] ToByteArray<T>(this T token)
        {
            if (token == null)
                return null;
            BinaryFormatter bf = new BinaryFormatter();
            using (MemoryStream ms = new MemoryStream())
            {
                bf.Serialize(ms, token);
                return ms.ToArray();
            }
        }

        public static T ToObject<T>(this byte[] tokenBytes)
        {
            MemoryStream memStream = new MemoryStream();
            BinaryFormatter binForm = new BinaryFormatter();
            memStream.Write(tokenBytes, 0, tokenBytes.Length);
            memStream.Seek(0, SeekOrigin.Begin);
            T obj = (T)binForm.Deserialize(memStream);
            return obj;
        }
    }
}
