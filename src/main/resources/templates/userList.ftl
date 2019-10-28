<#import "parts/common.ftl" as c>

<@c.page>
List of users

<table class="table table-sm">
    <thead>
    <tr>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
        <tr>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td><#list user.roles as role>${role}<#sep>, </#list></td>
            <td><a href="/user/${user.id}"><i class="fas fa-edit"></i></a></td>
        </tr>
    </#list>
    </tbody>
</table>
</@c.page>

