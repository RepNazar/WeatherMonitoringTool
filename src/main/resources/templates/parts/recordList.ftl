<#include "security.ftl">

<#list records as record>
    <tr>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.date}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.temperature}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windSpeed}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windAngle}</a>
        </td>
        <td>
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.pressure}</a>
        </td>
        <td>
            <#if record.author.id == currentUserId>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <a href="/user-records/${record.author.id}/delete/${record.id}">
                    <i class="fas fa-trash-alt"></i>
                </a>
            </#if>
        </td>
    </tr>
<#else>
    No Records
</#list>