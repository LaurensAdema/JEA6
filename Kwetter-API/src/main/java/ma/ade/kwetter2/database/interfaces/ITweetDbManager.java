/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.ade.kwetter2.database.interfaces;


import ma.ade.kwetter2.database.objects.Tweet;

import java.util.Collection;

/**
 *
 * @author laure
 */
public interface ITweetDbManager extends IBaseDbManager<Tweet>
{
    Collection<Tweet> search(String query);
    Collection<Tweet> getTweetsOf(long id);
}
