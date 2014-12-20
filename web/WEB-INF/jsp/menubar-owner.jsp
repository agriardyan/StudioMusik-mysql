<%-- 
    Document   : menubar-owner
    Created on : Dec 20, 2014, 11:37:19 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:out value="${pageContext.request.contextPath}/resources/semantic-ui/packaged/css/semantic.css" />" rel="stylesheet" type="text/css">        
    </head>
    <body>
        <div class="container">
            <div class="ui menu">
                <a class="active item" href="halamanutamaowner">
                    <i class="book icon"></i> LIHAT LAPORAN
                </a>
                <div class="ui pointing dropdown link item">
                    <i class="user icon"></i> MEMBER <i class="dropdown icon"></i>
                    <div class="menu">
                        <a class="item" id="reg" href="registrasi"><i class="add icon"></i>Registrasi Member</a>
                        <a class="item" id="del" href="underconstruction"><i class="dollar icon"></i>Hapus Member</a>
                    </div>
                </div>
                <div class="ui pointing dropdown link item">
                    <i class="user icon"></i> PEGAWAI <i class="dropdown icon"></i>
                    <div class="menu">
                        <a class="item" id="add" href="underconstruction"><i class="book icon"></i>Tambah Pegawai</a>
                        <a class="item" id="edit" href="underconstruction"><i class="add icon"></i>Edit Pegawai</a>
                        <a class="item" id="del" href="underconstruction"><i class="dollar icon"></i>Hapus Pegawai</a>
                    </div>
                </div>
                <div class="ui pointing dropdown link item">
                    <i class="user icon"></i> STUDIO <i class="dropdown icon"></i>
                    <div class="menu">
                        <a class="item" id="add" href="underconstruction"><i class="book icon"></i>Tambah Studio</a>
                        <a class="item" id="edit" href="underconstruction"><i class="add icon"></i>Edit Studio</a>
                        <a class="item" id="del" href="underconstruction"><i class="dollar icon"></i>Hapus Studio</a>
                    </div>
                </div>
                <!--a class="item" href="">
                    <i class="money icon"></i> UBAH HARGA
                </a-->
                <div class="right menu">
                    <form action="logout" method="POST">
                        <div class="ui dropdown link item">
                            <i class="user icon"></i> OWNER <i class="dropdown icon"></i>
                            <div class="menu">
                                <table class="ui basic table">
                                    <tr>
                                        <td>Nama</td>
                                        <td>${name}</td>
                                    </tr>
                                    <tr>
                                        <td >ID</td>
                                        <td>${username}</td>
                                    </tr>
                                </table>
                                <input class="ui fluid tiny submit button" type="submit" name="logoutAd" value="Logout">
                            </div>  
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="${pageContext.request.contextPath}/resources/semantic-ui/packaged/javascript/jquery-2.1.1.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/resources/semantic-ui/packaged/javascript/semantic.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {

            });
        </script>
    </body>
</html>
