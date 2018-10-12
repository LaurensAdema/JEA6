using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Interfaces
{
    public interface IBaseService<T>
    {
        Task<T> GetAsync(long id);
    
        Task<IEnumerable<T>> GetAllAsync();
    
        Task<T> CreateAsync(T entity);

        Task UpdateAsync(T entity);

        Task DeleteAsync(long id);
    }
}
