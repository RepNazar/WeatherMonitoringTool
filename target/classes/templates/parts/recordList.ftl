<#include "security.ftl">
<#list page.content as record>
    <tr data-id="${record.id}">
        <td data-type="date">
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.date}</a>
        </td>
        <td data-type="temperature">
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.temperature}</a>
        </td>
        <td data-type="windSpeed">
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windSpeed}</a>
        </td>
        <td data-type="windAngle">
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.windAngle}</a>
        </td>
        <td data-type="pressure">
            <a href="/user-records/${record.author.id}?record=${record.id}">${record.pressure}</a>
        </td>
        <td>
            <#if record.author.id == currentUserId>
                <form method="post" action="/user-records/${record.author.id}/delete">
                    <input type="hidden" name="id" value="${record.id}"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="form-group m-0">
                        <button type = "submit" class ="btn btn-link p-0" style="line-height: normal!important;">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                </form>
            </#if>
        </td>
    </tr>
<#else>
    <tr>
        <td>No Records</td>
    </tr>
</#list>

