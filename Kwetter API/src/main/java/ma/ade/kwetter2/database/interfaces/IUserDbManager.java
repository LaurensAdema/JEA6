/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.ade.kwetter2.database.interfaces;

import ma.ade.kwetter2.database.objects.User;

/**
 *
 * @author laure
 */
public interface IUserDbManager extends IBaseDbManager<User>
{
    User getByEmail(String email);
}
