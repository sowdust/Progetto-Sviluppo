/*
 * Copyright (C) 2014 glaxy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cryptohelper.model;

import java.sql.SQLException;

/**
 *
 * @author glaxy
 */
public interface MessaggioAstratto {

    public int getId();

    public String getTitolo();

    public String getTesto() throws SQLException;

    public String getTestoCifrato() throws SQLException;

    public String getLingua() throws SQLException;

    public boolean elimina(UserInfo studente) throws SQLException;

}
