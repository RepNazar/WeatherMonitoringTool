<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>

    <h4>Records by: ${user.username}</h4>

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/user-records/${user.id}" class="form-inline">
                <input type="text" name="filter" class="form-control"
                       value="${filter!}" placeholder="Date starts with">
                <button type="submit" class="btn btn-primary ml-2">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>
    </div>

    <#if page.getTotalElements() gt 25>
        <@p.pager "/user-records/${user.id}" page/>
    </#if>

    <div class="form-group mt-3">
        <table class="table table-sm table-striped">
            <thead>
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Temperature</th>
                <th scope="col">WindSpeed</th>
                <th scope="col">WindAngle</th>
                <th scope="col">Pressure</th>
                <th scope="col"></th>
            </tr>
            </thead>

            <tbody>
            <#if isCurrentUser>
                <form method="post" action="/user-records/${user.id}" enctype="multipart/form-data">
                    <#include "parts/recordEdit.ftl" />
                </form>
            </#if>

            <#include "parts/recordList.ftl" />
            </tbody>
        </table>
    </div>

    <#if page.getTotalElements() gt 25>
        <@p.pager "/user-records/${user.id}" page/>
    </#if>

</@c.page>
