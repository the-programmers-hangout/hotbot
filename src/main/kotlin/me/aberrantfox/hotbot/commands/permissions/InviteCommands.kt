package me.aberrantfox.hotbot.commands.permissions

import me.aberrantfox.hotbot.listeners.antispam.InviteWhitelist
import me.aberrantfox.hotbot.services.LoggingService
import me.aberrantfox.kjdautils.api.dsl.CommandSet
import me.aberrantfox.kjdautils.api.dsl.commands
import me.aberrantfox.kjdautils.extensions.jda.fullName
import me.aberrantfox.kjdautils.internal.arguments.WordArg
import me.aberrantfox.kjdautils.internal.logging.BotLogger

@CommandSet("invite")
fun inviteCommands(loggingService: LoggingService, inviteWhitelist: InviteWhitelist) =
    commands {
        command("whitelistinvite") {
            description = "Allow an invite to be posted."
            expect(WordArg("Invite URL"))
            execute {
                val inv = it.args[0] as String
                inviteWhitelist.set.add(inv)
                it.respond("Added $inv to the invite whitelist, it can now be posted freely.")
                loggingService.logInstance.info("$inv was added to the whitelist by ${it.author.fullName()} and it can now be posted freely")
            }
        }

        command("unwhitelistinvite") {
            description = "Remove an invitation from the whitelist."
            expect(WordArg("Invite URL"))
            execute {
                val inv = it.args[0] as String
                inviteWhitelist.set.remove(inv)
                it.respond("$inv has been removed from the invite whitelist, posting it the configured amount of times will result in a ban.")
                loggingService.logInstance.info("$inv was removed from the whitelist by ${it.author.fullName()}, the bot will now delete messages containing this invite.")
            }
        }
    }
