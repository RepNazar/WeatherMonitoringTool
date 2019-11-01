<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Date starts with">
                <button type="submit" class="btn btn-primary ml-2"><i class="fas fa-search"></i></button>
            </form>
        </div>
    </div>

    <#if page.getTotalElements() gt 25>
        <@p.pager "/" page/>
    </#if>

    <table class="table table-striped">
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
        No Records
        </tbody>
    </table>
    <#if page.getTotalElements() gt 25>
        <@p.pager "/" page/>
    </#if>

</@c.page>