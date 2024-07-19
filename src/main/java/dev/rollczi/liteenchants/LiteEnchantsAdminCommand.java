package dev.rollczi.liteenchants;

import dev.rollczi.liteenchants.reload.ReloadManager;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

@Command(name = "cxadmin")
@Permission("liteenchants.admin")
class LiteEnchantsAdminCommand {

    private final ReloadManager reloadManager;

    public LiteEnchantsAdminCommand(ReloadManager reloadManager) {
        this.reloadManager = reloadManager;
    }

    @Async
    @Execute(name = "reload")
    void reload() {
        reloadManager.reload();
    }

}
